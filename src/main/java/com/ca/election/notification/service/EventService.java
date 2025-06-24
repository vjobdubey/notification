package com.ca.election.notification.service;


import com.ca.election.notification.model.Event;
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
        event.setEventId("EVT100");
        event.setCreatedAt(Instant.parse("2025-05-26T12:00:00Z"));
        event.setUpdatedAt(Instant.parse("2025-06-19T07:34:42.150Z"));
        event.setClaimedAt(Instant.parse("2025-06-19T07:34:41.980Z"));
        event.setEmailStatus("COMPLETED");

        // ----------- Positions -----------
        Event.Position position = new Event.Position();

        Event.Risk risk = new Event.Risk();
        risk.setId("RSK001");
        risk.setType("Equity");
        risk.setDate(Instant.parse("2025-06-15T00:00:00Z"));
        risk.setSourceSystem("Quantum");
        risk.setSourceSystemId("SRC001");

        Event.Instrument instrument = new Event.Instrument();
        instrument.setSedol("B1Y2QL5");
        instrument.setRic("XYZ.N");
        instrument.setIsin("US1234567890");
        instrument.setName("XYZ Corp");
        instrument.setType("Common Stock");
        risk.setInstrument(instrument);
        position.setRisk(risk);

        Event.Holder holder = new Event.Holder();
        holder.setId("HLD10001");
        holder.setLegalEntity("Alpha Investments");
        holder.setGroup("Global Finance Group");
        holder.setActivity("Investment Banking");
        holder.setAccount("ACC90876");
        holder.setCountry("USA");
        holder.setLocation("New York");
        holder.setSite("Main HQ");
        holder.setClientCode("CL001");
        holder.setClientType("Institutional");
        holder.setBackToBack(true);
        position.setHolder(holder);

        position.setSettled(true);

        Event.SBL sbl = new Event.SBL();
        sbl.setActive(false);
        position.setSbl(sbl);

        Event.SBLThirdParty sblThirdParty = new Event.SBLThirdParty();
        sblThirdParty.setProvider("TPBank");
        sblThirdParty.setStatus("Inactive");
        position.setSblThirdParty(sblThirdParty);

        Event.NonStandard nonstandard = new Event.NonStandard();
        nonstandard.setOverride(false);
        position.setNonstandard(nonstandard);

        Event.Synthetic synthetic = new Event.Synthetic();
        synthetic.setFlag(true);
        position.setSynthetic(synthetic);

        event.setPositions(List.of(position));

        // ----------- Transactions -----------
        Event.Trade calypso = new Event.Trade();
        calypso.setTradeId("CAL001");
        calypso.setBookingId("BK001");
        calypso.setAmount(100000.0);
        calypso.setCurrency("USD");

        Event.Trade quantum = new Event.Trade();
        quantum.setTradeId("QNT001");
        quantum.setBookingId("BK002");
        quantum.setAmount(100000.0);
        quantum.setCurrency("USD");

        Event.Transactions transactions = new Event.Transactions();
        transactions.setCalypso(calypso);
        transactions.setQuantum(quantum);
        event.setTransactions(transactions);

        // ----------- Election Management -----------
        Event.ElectionItem item = new Event.ElectionItem();
        item.setType("Cash");
        item.setQuantity(100);

        Event.GiveElection giveElection = new Event.GiveElection();
        giveElection.setItems(List.of(item));
        giveElection.setTotalQuantity(100);
        giveElection.setElectionQuantity(90);

        Event.ReceiveElection receiveElection1 = new Event.ReceiveElection();
        receiveElection1.setDetails("Shares to receive");

        Event.Arbitrage arbitrage1 = new Event.Arbitrage();
        arbitrage1.setStrategy("Market Neutral");

        Event.Balance balance1 = new Event.Balance();
        balance1.setAmount(10);

        Event.ElectionOption citilo = new Event.ElectionOption();
        citilo.setGiveElection(giveElection);
        citilo.setRecieveElection(receiveElection1);
        citilo.setArbitrage(arbitrage1);
        citilo.setBalance(balance1);

        Event.ReceiveElection receiveElection2 = new Event.ReceiveElection();
        receiveElection2.setDetails("Synthetic payout");

        Event.Arbitrage arbitrage2 = new Event.Arbitrage();
        arbitrage2.setStrategy("Pair Trade");

        Event.Balance balance2 = new Event.Balance();
        balance2.setAmount(5);

        Event.ElectionOption synth = new Event.ElectionOption();
        synth.setGiveElection(giveElection);
        synth.setRecieveElection(receiveElection2);
        synth.setArbitrage(arbitrage2);
        synth.setBalance(balance2);
        synth.setStrategy("Synthetic Yield");

        Event.Election election = new Event.Election();
        election.setCITILO(citilo);
        election.setSYNTH(synth);

        Event.Configuration config = new Event.Configuration();
        config.setDeadline(Instant.parse("2025-06-25T23:59:59Z"));

        Event.Options options = new Event.Options();
        options.setAvailableOptions(List.of("Cash", "Stock", "Synthetic"));

        Event.ElectionManagement em = new Event.ElectionManagement();
        em.setOptionChangeDetails("UpdatedOptionX");
        em.setElectionOptionStatus("Pending");
        em.setElectionUpdatedBy("systemUser");
        em.setElection(election);
        em.setPrices(Map.of("USD", 100.5, "EUR", 95.2));
        em.setConfiguration(config);
        em.setOptions(options);
        em.setCustodyDeadline(Instant.parse("2025-06-20T12:00:00Z"));

        event.setElectionManagement(em);

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

    //to be deleted later
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

