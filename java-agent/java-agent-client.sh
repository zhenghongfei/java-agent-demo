#!/bin/bash

java -Xbootclasspath/a:/usr/lib/jvm/java-8-oracle/lib/tools.jar -jar ./target/java-agent-0.1-jar-with-dependencies.jar "$@"