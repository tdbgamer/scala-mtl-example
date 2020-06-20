{ pkgs ? import <nixpkgs> {} }:

let
  jdkVersion = pkgs.openjdk11;
in
  pkgs.mkShell {

    # build-time dependencies
    buildInputs = [
      jdkVersion
      pkgs.sbt
      pkgs.scalafmt
    ];

    # Runtime dependencies
    propagatedBuildInputs = [
      # C libraries required to build
    ];


    shellHook = ''
      export JAVA_HOME='${jdkVersion}'
      export SBT_HOME='${pkgs.sbt}'
    '';
  }
