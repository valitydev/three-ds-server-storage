package com.rbkmoney.threeds.server.storage.repository;

import com.rbkmoney.threeds.server.storage.entity.CardRangeEntity;
import com.rbkmoney.threeds.server.storage.entity.CardRangePk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CardRangeRepository extends JpaRepository<CardRangeEntity, CardRangePk> {

    @Transactional
    void deleteAllByPkProviderId(String providerId);

    List<CardRangeEntity> findByPkProviderId(String providerId);

    boolean existsCardRangeEntitiesByPkProviderIdIs(String providerId);

    @Query("select " +
            "case when count(cr)> 0 then false else true end " +
            "from CardRangeEntity cr " +
            "where cr.pk.providerId = ?1 " +
            "and ((cr.pk.rangeStart > ?2 and cr.pk.rangeStart <= ?3) " +
            "or (cr.pk.rangeEnd >= ?2 and cr.pk.rangeEnd < ?3) " +
            "or (cr.pk.rangeEnd < ?3 and cr.pk.rangeStart > ?2) " +
            "or (cr.pk.rangeEnd > ?3 and cr.pk.rangeStart < ?2)" +
            "or (cr.pk.rangeEnd = ?2 and cr.pk.rangeEnd = ?3) " +
            "or (cr.pk.rangeStart = ?2 and cr.pk.rangeStart = ?3))"
    )
    boolean existsFreeSpaceForNewCardRange(String providerId, long startRange, long endRange);

    boolean existsCardRangeEntityByPkEquals(CardRangePk cardRangePk);

    @Query("select " +
            "cr.pk.providerId " +
            "from CardRangeEntity cr " +
            "where cr.pk.rangeEnd >= ?1 and cr.pk.rangeStart <= ?1")
    Page<String> getProviderIds(long accountNumber, Pageable pageable);

}
