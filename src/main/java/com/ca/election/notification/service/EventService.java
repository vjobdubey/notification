package com.ca.election.notification.service;


import com.ca.election.notification.model.*;
import com.ca.election.notification.repository.EventDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
public class EventService {

    @Autowired
    private EventDao repository;

    public Mono<Event> saveSampleEvent() {
        Event event = new Event();
        event.setEventId("EVT10001");
        event.setCreatedAt(Instant.parse("2025-05-26T12:00:00Z"));
        event.setUpdatedAt(Instant.parse("2025-06-19T07:34:42.150Z"));
        event.setClaimedAt(Instant.parse("2025-06-19T07:34:41.980Z"));
        event.setEmailStatus("COMPLETED");

        // --- Instrument ---
        Instrument instrument = new Instrument();
        instrument.setSedol("B1Y2QL5");
        instrument.setRic("RIC123");
        instrument.setIsin("ISIN456");
        instrument.setName("ABC Corp");
        instrument.setType("Equity");

        // --- Holder ---
        Holder holder = new Holder();
        holder.setHid("HLD10001");
        holder.setLegalEntity("Alpha Investments");
        holder.setGroup("Global Finance Group");
        holder.setActivity("Investment Banking");
        holder.setAccount("ACC90876");
        holder.setCountry("USA");
        holder.setLocation("New York");
        holder.setSite("Main HQ");
        holder.setClientCode("CL001");
        holder.setClientType("Institutional");
        holder.setIsBackToBack(true);

        // --- Risk ---
        Risk risk = new Risk();
        risk.setRid("EVT10001");
        risk.setType("Dividend");
        risk.setDate(Instant.parse("2025-06-27T00:00:00Z"));
        risk.setSourceSystem("Quantum");
        risk.setSourceSystemId("SRC001");
        risk.setInstrument(instrument);

        Position position = new Position();
        position.setRisk(risk);
        position.setHolder(holder);
        position.setHolder(holder);
        position.setSettled(true);
        position.setSbl(new SBL(false));
        position.setSblThirdParty(new SBLThirdParty("TPBank", "Inactive"));
        position.setNonstandard(new NonStandard(false));
        position.setSynthetic(new Synthetic(true));


        // --- Transaction ---
        Calypso calypso = new Calypso();
        calypso.setTradeId("CAL490");
        calypso.setBookingId("BK1");
        calypso.setAmount(100000.0);
        calypso.setCurrency("USD");

        Quantum quantum = new Quantum();
        quantum.setTradeId("QTX990");
        quantum.setAmount(50000.0);
        quantum.setBookingId("BK2");
        quantum.setCurrency("USD");

        Transaction transaction = new Transaction();
        transaction.setCalypso(calypso);
        transaction.setQuantum(quantum);

        event.setTransactions(transaction);

        // ---- CITILO Option ----
        GiveElection giveCitilo = new GiveElection();
        giveCitilo.setTotalQuantity(100);
        giveCitilo.setElectionQuantity(90);
        giveCitilo.setItems(List.of(
                new GiveElection.ElectionItem("Cash", 100)
        ));

        ReceiveElection receiveCitilo = new ReceiveElection();
        receiveCitilo.setDetails("Shares to receive");

        Arbitrage arbitrageCitilo = new Arbitrage();
        arbitrageCitilo.setStrategy("Market Neutral");

        Balance balanceCitilo = new Balance();
        balanceCitilo.setAmount(10);

        ElectionOption citiloOption = new ElectionOption();
        citiloOption.setGiveElection(giveCitilo);
        citiloOption.setRecieveElection(receiveCitilo);
        citiloOption.setArbitrage(arbitrageCitilo);
        citiloOption.setBalance(balanceCitilo);

    // ---- SYNTH Option ----
        GiveElection giveSynth = new GiveElection();
        giveSynth.setTotalQuantity(80);
        giveSynth.setElectionQuantity(75);
        giveSynth.setItems(List.of(
                new GiveElection.ElectionItem("Synthetic", 80)
        ));

        ReceiveElection receiveSynth = new ReceiveElection();
        receiveSynth.setDetails("Synthetic payout");

        Arbitrage arbitrageSynth = new Arbitrage();
        arbitrageSynth.setStrategy("Pair Trade");

        Balance balanceSynth = new Balance();
        balanceSynth.setAmount(5);

        ElectionOption synthOption = new ElectionOption();
        synthOption.setGiveElection(giveSynth);
        synthOption.setRecieveElection(receiveSynth);
        synthOption.setArbitrage(arbitrageSynth);
        synthOption.setBalance(balanceSynth);

    // ---- Final Election Map ----
        Election election = new Election();
        election.setCITILO(citiloOption);
        election.setSYNTH(synthOption);

        // --- Election Management ---
        ElectionManagement electionManagement = new ElectionManagement();
        electionManagement.setOptionChangeDetails("Updated option Y");
        electionManagement.setElectionOptionStatus("PENDING");
        electionManagement.setElectionUpdatedBy("admin");
        electionManagement.setPrices(Map.of("USD", 100.5, "EUR", 95.2));

        Configuration config = new Configuration();
        config.setDeadline(Instant.parse("2025-06-25T23:59:59Z"));
        electionManagement.setConfiguration(config);

        Options opts = new Options();
        opts.setAvailableOptions(List.of("Cash", "Stock", "Synthetic"));
        electionManagement.setOptions(opts);

        electionManagement.setCustodyDeadline(Instant.parse("2025-06-25T23:59:59Z"));

        event.setElectionManagement(electionManagement);

        return repository.save(event);
    }

    public Mono<Event> getEventBySedol(String sedolId) {
        return repository.findBySedol(sedolId);
    }


    public Mono<Event> getEventById(String id) {
        return repository.findById(id);
    }

    public Flux<Event> getEventList() {
        return repository.findAll();
    }

    public Mono<Event> create(Event event) {
        return repository.save(event);
    }

    public Mono<Event> update(String id, Event event) {
        return repository.findById(id)
                .flatMap(existing -> {
                    existing.setEmailStatus(event.getEmailStatus());
                    existing.setUpdatedAt(Instant.now());
                    return repository.save(existing);
                });
    }

    public Mono<Void> delete(String id) {
        return repository.deleteById(id);
    }
}

