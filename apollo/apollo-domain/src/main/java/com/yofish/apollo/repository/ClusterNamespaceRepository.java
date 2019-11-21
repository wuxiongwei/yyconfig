package com.yofish.apollo.repository;

import com.yofish.apollo.domain.App;
import com.yofish.apollo.domain.ClusterNamespace;
import com.yofish.apollo.domain.Namespace;
import com.yofish.apollo.enums.Envs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

/**
 * Created on 2018/2/5.
 *
 * @author zlf
 * @since 1.0
 */
public interface ClusterNamespaceRepository extends JpaRepository<ClusterNamespace, Long> {

    ClusterNamespace findByAppIdAndEnvAndClusterNameAndnamespaceName(String appId, Envs env, String clusterName, String namespaceName);



}
