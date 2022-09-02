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