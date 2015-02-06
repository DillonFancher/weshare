#!/usr/bin/env bash

# Run from root dir
cd "$( dirname "${BASH_SOURCE[0]}" )/.." 

JAVA_OPTS="-Dorg.slf4j.simpleLogger.defaultLogLevel=info"
sbt "migratifier/run"
