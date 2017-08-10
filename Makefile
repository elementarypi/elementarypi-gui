#!/bin/sh
# ElementaryPi Makefile

all: ElementaryPi.class
ElementaryPi.class: ElementaryPi.java
	javac $<
clean:
	rm -Rf ./*.class
