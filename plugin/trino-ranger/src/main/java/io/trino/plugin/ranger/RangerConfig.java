/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package io.trino.plugin.ranger;

import io.airlift.configuration.Config;
import io.airlift.configuration.ConfigDescription;

public class RangerConfig
{
    private String keytab;
    private String principal;
    private boolean useUgi;
    private String hadoopConfigPath;
    private String auditResourcesConfigPath;
    private String securityResourcesConfigPath;
    private String policyManagerSSLConfigPath;

    public RangerConfig()
    {
        useUgi = false;
    }

    public String getKeytab()
    {
        return keytab;
    }

    @Config("ranger.keytab")
    @ConfigDescription("Keytab for authentication against Ranger")
    @SuppressWarnings("unused")
    public RangerConfig setKeytab(String keytab)
    {
        this.keytab = keytab;
        return this;
    }

    public String getPrincipal()
    {
        return principal;
    }

    @Config("ranger.principal")
    @ConfigDescription("Principal for authentication against Ranger with keytab")
    @SuppressWarnings("unused")
    public RangerConfig setPrincipal(String principal)
    {
        this.principal = principal;
        return this;
    }

    public boolean isUseUgi()
    {
        return useUgi;
    }

    @Config("ranger.use_ugi")
    @ConfigDescription("Use Hadoop User Group Information instead of Trino groups")
    @SuppressWarnings("unused")
    public RangerConfig setUseUgi(boolean useUgi)
    {
        this.useUgi = useUgi;
        return this;
    }

    @Config("ranger.hadoop_config")
    @ConfigDescription("Path to hadoop configuration. Defaults to trino-ranger-site.xml in classpath")
    @SuppressWarnings("unused")
    public RangerConfig setHadoopConfigPath(String hadoopConfigPath)
    {
        this.hadoopConfigPath = hadoopConfigPath;
        return this;
    }

    public String getHadoopConfigPath()
    {
        return hadoopConfigPath;
    }

    @Config("ranger.audit_resource")
    @ConfigDescription("Full path to the audit config files (ranger.audit_resource)")
    @SuppressWarnings("unused")
    public RangerConfig setAuditResourcesConfigPath(String auditResourcesConfigPath)
    {
        this.auditResourcesConfigPath = auditResourcesConfigPath;
        return this;
    }

    public String getAuditResourcesConfigPath()
    {
        return auditResourcesConfigPath;
    }

    @Config("ranger.security_resource")
    @ConfigDescription("Full path to the security config file (ranger-trino-security.xml)")
    @SuppressWarnings("unused")
    public RangerConfig setSecurityResourcesConfigPath(String securityResourcesConfigPath)
    {
        this.securityResourcesConfigPath = securityResourcesConfigPath;
        return this;
    }

    public String getSecurityResourcesConfigPath()
    {
        return securityResourcesConfigPath;
    }

    @Config("ranger.policy_manager_ssl_resource")
    @ConfigDescription("Full path to the ranger policy manager config file (ranger-policymgr-ssl.xml)")
    @SuppressWarnings("unused")
    public RangerConfig setPolicyManagerSSLConfigPath(String policyManagerSSLConfigPath)
    {
        this.policyManagerSSLConfigPath = policyManagerSSLConfigPath;
        return this;
    }

    public String getPolicyManagerSSLConfigPath()
    {
        return policyManagerSSLConfigPath;
    }
}
