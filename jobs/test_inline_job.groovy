multibranchPipelineJob('test-inline-job') {
    branchSources {
        github {
            id('test-inline-job')
            repoOwner('solarwinds-anton-orlov')
            repository('jenkins-test-project')
            includes('*')
        }
    }
    factory {
        inlineDefinitionBranchProjectFactory {
            markerFile('pipeline.json')
            sandbox(false)
            script("""node {
    checkout scm
    def config = readJSON file: 'pipeline.json'
    assert config.version =~ /[\\d]+\\.[\\d]+/
    library "mpl-nested@v\${config.version}"
    MPLScripted()
}""")
        }
    }
}

multibranchPipelineJob('test-jte-job') {
    branchSources {
        github {
            id('test-jte-job')
            repoOwner('solarwinds-anton-orlov')
            repository('jenkins-test-project')
            includes('*')
        }
    }
    factory {
        templateBranchProjectFactory {
            configurationPath('pipeline_config.groovy')
            scriptPath('Jenkinsfile')
        }
    }
    properties {
        templateConfigFolderProperty {
            tier {
                configurationProvider {
                    scmPipelineConfigurationProvider {
                        scm {
                            gitSCM {
                                userRemoteConfigs {
                                    userRemoteConfig {
                                        name("test-jte-config")
                                        url("https://github.com/solarwinds-anton-orlov/jenkins-jte")
                                        refspec("+refs/heads/main:refs/remotes/origin/main")
                                        credentialsId("")
                                    }
                                }
                                branches {
                                    branchSpec {
                                        name("*/main")
                                    }
                                }
                                browser {
                                    gitWeb {
                                        repoUrl("https://github.com/solarwinds-anton-orlov/jenkins-jte")
                                    }
                                }
                                gitTool("git")
                            }
                        }
                    }
                }
                librarySources {
                    librarySource {
                        libraryProvider {
                            scmLibraryProvider {
                                baseDir("libraries")
                                scm {
                                    gitSCM {
                                        userRemoteConfigs {
                                            userRemoteConfig {
                                                name("test-jte-library")
                                                url("https://github.com/solarwinds-anton-orlov/jenkins-jte")
                                                refspec("+refs/heads/main:refs/remotes/origin/main")
                                                credentialsId("")
                                            }
                                        }
                                        branches {
                                            branchSpec {
                                                name("*/main")
                                            }
                                        }
                                        browser {
                                            gitWeb {
                                                repoUrl("https://github.com/solarwinds-anton-orlov/jenkins-jte")
                                            }
                                        }
                                        gitTool("git")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

multibranchPipelineJob('test-cfg-job') {
    branchSources {
        github {
            id('test-cfg-job')
            repoOwner('solarwinds-anton-orlov')
            repository('jenkins-test-project')
            includes('*')
        }
    }
    factory {
        configDrivenWorkflowBranchProjectFactory {
            scriptPath(".pipeline.yaml")
            jenkinsFileScm {
                gitSCM {
                    userRemoteConfigs {
                        userRemoteConfig {
                            name("test-cfg-config")
                            url("https://github.com/solarwinds-anton-orlov/jenkins-cfg-pipeline")
                            refspec("+refs/heads/main:refs/remotes/origin/main")
                            credentialsId("")
                        }
                    }
                    branches {
                        branchSpec {
                            name("*/main")
                        }
                    }
                    browser {
                        gitWeb {
                            repoUrl("https://github.com/solarwinds-anton-orlov/jenkins-cfg-pipeline")
                        }
                    }
                    gitTool("git")
                }
            }
        }
    }
}