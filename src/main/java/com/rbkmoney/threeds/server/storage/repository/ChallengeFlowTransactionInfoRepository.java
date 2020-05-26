package com.rbkmoney.threeds.server.storage.repository;

import com.rbkmoney.threeds.server.storage.entity.ChallengeFlowTransactionInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChallengeFlowTransactionInfoRepository extends JpaRepository<ChallengeFlowTransactionInfoEntity, String> {

    Optional<ChallengeFlowTransactionInfoEntity> findByTransactionId(String transactionId);
}
