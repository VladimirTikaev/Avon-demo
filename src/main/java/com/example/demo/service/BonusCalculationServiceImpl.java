package com.example.demo.service;

import com.example.demo.constant.RemunerationLevel;
import com.example.demo.entity.ClientEntity;
import com.example.demo.entity.ClientSalesEntity;
import com.example.demo.entity.GenerationEntity;
import com.example.demo.entity.GenerationSquadEntity;
import com.example.demo.repository.ClientConnectionRepository;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.ClientSalesRepository;
import com.example.demo.service.api.BonusCalculationService;
import com.example.demo.util.CalculationHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.example.demo.constant.RemunerationLevel.K1;
import static com.example.demo.constant.RemunerationLevel.K2;

@Service
public class BonusCalculationServiceImpl implements BonusCalculationService {

    public static final int FIRST_GENERATION = 1;
    public static final String REPRESENTATIVE = "Представитель";
    public static final String BUSINESS_PARTNER = "Бизнес-партнер";

    private final ClientRepository clientRepository;
    private final ClientSalesRepository clientSalesRepository;

    public BonusCalculationServiceImpl(ClientRepository clientRepository, ClientSalesRepository clientSalesRepository, ClientConnectionRepository clientConnectionRepository) {
        this.clientRepository = clientRepository;
        this.clientSalesRepository = clientSalesRepository;
    }

    @Override
    public Double calculateExtraBonus(Long clientId) {
        ClientEntity client = clientRepository.findById(clientId).orElseThrow();
        RemunerationLevel clientRemunerationLevel = client.getLevel().getName();
        if (clientRemunerationLevel.isCoordinator()) {
            if (isMeetConditionForCoordinator(client)) {
                return 1.0;
            }
        } else if (clientRemunerationLevel.isLeader()) {
            if (isMeetConditionForLeader(client)) {
                return 1.0;
            }
        } else if (clientRemunerationLevel.isDirector()) {
            if (isMeetConditionForDirector(client)) {
                return 0.4;
            }
        }
        return 0.0;
    }

    private boolean isMeetConditionForCoordinator(ClientEntity client) {
        LocalDate now = LocalDate.now();
        String actualName = CalculationHelper.calculateCompanyName(now.getYear(), now.getMonthValue());
        String previousName = CalculationHelper.calculateCompanyName(now.getYear(), now.getMonthValue() - 1);
        String beforePreviousName = CalculationHelper.calculateCompanyName(now.getYear(), now.getMonthValue() - 2);

        Optional<ClientSalesEntity> actualCompany = clientSalesRepository.findByClientAndCompanyName(client, actualName).stream()
                .max(Comparator.comparingInt(ClientSalesEntity::getVersion));
        Optional<ClientSalesEntity> previousCompany = clientSalesRepository.findByClientAndCompanyName(client, previousName).stream()
                .max(Comparator.comparingInt(ClientSalesEntity::getVersion));
        Optional<ClientSalesEntity> beforePreviousCompany = clientSalesRepository.findByClientAndCompanyName(client, beforePreviousName).stream()
                .max(Comparator.comparingInt(ClientSalesEntity::getVersion));

        if (actualCompany.isEmpty() || previousCompany.isEmpty() || beforePreviousCompany.isEmpty()) {
            return false;
        }

        GenerationEntity actualFirstGeneration = getFirstGeneration(actualCompany.get());
        GenerationEntity previousFirstGeneration = getFirstGeneration(previousCompany.get());
        GenerationEntity beforePreviousFirstGeneration = getFirstGeneration(beforePreviousCompany.get());

        List<GenerationSquadEntity> actualFirstGenerationSquad = actualFirstGeneration.getGenerationSquadList();
        List<GenerationSquadEntity> previousFirstGenerationSquad = previousFirstGeneration.getGenerationSquadList();
        List<GenerationSquadEntity> beforePreviousFirstGenerationSquad = beforePreviousFirstGeneration.getGenerationSquadList();

        long actualRepresentativeCount = countRepresentatives(actualFirstGenerationSquad);
        long previousRepresentativeCount = countRepresentatives(previousFirstGenerationSquad);
        long beforePreviousRepresentativeCount = countRepresentatives(beforePreviousFirstGenerationSquad);

        return actualRepresentativeCount > previousRepresentativeCount && previousRepresentativeCount > beforePreviousRepresentativeCount;
    }

    private boolean isMeetConditionForLeader(ClientEntity client) {
        LocalDate now = LocalDate.now();
        String actualName = CalculationHelper.calculateCompanyName(now.getYear(), now.getMonthValue());
        String previousName = CalculationHelper.calculateCompanyName(now.getYear(), now.getMonthValue() - 3);

        Optional<ClientSalesEntity> actualCompany = clientSalesRepository.findByClientAndCompanyName(client, actualName).stream()
                .max(Comparator.comparingInt(ClientSalesEntity::getVersion));
        Optional<ClientSalesEntity> previousCompany = clientSalesRepository.findByClientAndCompanyName(client, previousName).stream()
                .max(Comparator.comparingInt(ClientSalesEntity::getVersion));

        if (actualCompany.isEmpty() || previousCompany.isEmpty()) {
            return false;
        }

        GenerationEntity actualFirstGeneration = getFirstGeneration(actualCompany.get());
        GenerationEntity previousFirstGeneration = getFirstGeneration(previousCompany.get());

        List<GenerationSquadEntity> actualFirstGenerationSquad = actualFirstGeneration.getGenerationSquadList();
        List<GenerationSquadEntity> previousFirstGenerationSquad = previousFirstGeneration.getGenerationSquadList();

        long actualBusinessPartnerCount = countBusinessPartners(actualFirstGenerationSquad);
        long previousBusinessPartnerCount = countBusinessPartners(previousFirstGenerationSquad);

        return actualBusinessPartnerCount > previousBusinessPartnerCount;
    }

    private boolean isMeetConditionForDirector(ClientEntity client) {
        RemunerationLevel clientLevel = client.getLevel().getName();
        return clientLevel != K1 && clientLevel != K2;
        // TODO: 4/18/2023 добавить рассчет бонуса
    }

    private GenerationEntity getFirstGeneration(ClientSalesEntity clientSales) {
        return clientSales.getGenerationList().stream().filter(e -> e.getGenerationLevel() == FIRST_GENERATION)
                .reduce((a, b) -> {
                    throw new IllegalStateException("Too many elements match the predicate");
                })
                .orElseThrow(() -> new IllegalStateException("No element matches the predicate"));
    }

    private long countRepresentatives(List<GenerationSquadEntity> generationSquad) {
        return generationSquad.stream()
                .filter(e -> Objects.equals(e.getClientType(), REPRESENTATIVE))
                .count();
    }

    private long countBusinessPartners(List<GenerationSquadEntity> generationSquad) {
        return generationSquad.stream()
                .filter(e -> Objects.equals(e.getClientType(), BUSINESS_PARTNER))
                .filter(e -> e.getClientEntity().getLevel() != null)
                .count();
    }
}
