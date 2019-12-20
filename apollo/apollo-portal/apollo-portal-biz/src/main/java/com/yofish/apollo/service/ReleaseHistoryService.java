package com.yofish.apollo.service;

import com.google.gson.Gson;
import com.yofish.apollo.domain.Release;
import com.yofish.apollo.domain.ReleaseHistory;
import com.yofish.apollo.repository.ReleaseHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
@Service
public class ReleaseHistoryService {
  private Gson gson = new Gson();

  @Autowired
  private ReleaseHistoryRepository releaseHistoryRepository;
//  @Autowired
//  private AuditService auditService;


  public Page<ReleaseHistory> findReleaseHistoriesByNamespace(Release release, Pageable
                                                                  pageable) {
    return releaseHistoryRepository.findReleaseHistoriesByRelease(release, pageable);
  }

  public Page<ReleaseHistory> findByReleaseIdAndOperation(Release release, int operation, Pageable page) {
    return releaseHistoryRepository.findReleaseHistorysByReleaseAndOperationOrderByIdDesc(release, operation, page);
  }

  public Page<ReleaseHistory> findByPreviousReleaseIdAndOperation(long previousReleaseId, int operation, Pageable page) {
    return releaseHistoryRepository.findByPreviousReleaseIdAndOperationOrderByIdDesc(previousReleaseId, operation, page);
  }

  @Transactional
  public ReleaseHistory createReleaseHistory(String appId, String clusterName, String
      namespaceName, String branchName, long releaseId, long previousReleaseId, int operation,
                                             Map<String, Object> operationContext) {
    ReleaseHistory releaseHistory = new ReleaseHistory();
//    releaseHistory.setAppId(appId);
//    releaseHistory.setClusterName(clusterName);
//    releaseHistory.setNamespaceName(namespaceName);
//    releaseHistory.setBranchName(branchName);
//    releaseHistory.setReleaseId(releaseId);
//    releaseHistory.setPreviousReleaseId(previousReleaseId);
    releaseHistory.setOperation(operation);
    if (operationContext == null) {
      releaseHistory.setOperationContext("{}"); //default empty object
    } else {
      releaseHistory.setOperationContext(gson.toJson(operationContext));
    }

    releaseHistoryRepository.save(releaseHistory);

//    auditService.audit(ReleaseHistory.class.getSimpleName(), releaseHistory.getId(),
//                       Audit.OP.INSERT, releaseHistory.getDataChangeCreatedBy());

    return releaseHistory;
  }

  @Transactional
  public int batchDelete(String appId, String clusterName, String namespaceName, String operator) {
    return releaseHistoryRepository.batchDelete(appId, clusterName, namespaceName, operator);
  }

  public void createReleaseHistory(Release release, Long id, int rollback, Object o) {

  }
}