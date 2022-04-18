
BASES = rpm

default: $(BASES)

pull:
	docker pull artprod.dev.bloomberg.com/dataops/bnef_analyst_base_intellij:latest

rpm:
	rm -f build.txt
	rm -rf core/trino-server-rpm/target
	mvn -e -X install -DskipTests -pl '!docs' 2>&1 | tee build.txt

ranger:
	rm -f build_ranger.txt
	mvn -e -X install -DskipTests -pl plugin/trino-ranger 2>&1 | tee build_ranger.txt

resume:
	rm -f build_ranger.txt
	mvn -e -X install -DskipTests -pl plugin/trino-ranger -rf :trino-ranger 2>&1 | tee build_ranger.txt

clean:
	mvn -e -X clean
	find . -type d -name target -exec rm -rf {} \;

attach:
	docker exec -it ${USER}-intellij bash

bash:
	/usr/local/bin/startup_intellij.sh bash

ui:
	/usr/local/bin/startup_intellij.sh

depends:
	mvn dependency:tree 2>&1 | tee dependency.txt

# When already inside the intellij docker container, run this to bring up IntelliJ
intellij:
	/opt/intellij/bin/idea.sh

cli:
	client/trino-cli/target/trino-cli-374-executable.jar  --server=http://localhost:8081 --user ${USER}
