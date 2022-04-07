#!/bin/bash

get_version() {
	python3 -c "
from lxml import etree
tree = etree.parse('pom.xml')
[version] = tree.xpath('/ns:project/ns:version/text()', namespaces={'ns': 'http://maven.apache.org/POM/4.0.0'})
print(version)
"
}

#version="$(get_version)"
version="$(
	mvn -q -Dexec.executable=echo -Dexec.args='${project.version}' --non-recursive exec:exec
)"
if [[ $? != 0 ]]
then
	echo failed to extract version >/dev/stderr
	exit 1
fi

mvn clean package install -DskipTests
docker build . -t demo-library --build-arg VERSION="${version}"
