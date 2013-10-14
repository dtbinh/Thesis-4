(defproject jsim "0.1.0-SNAPSHOT"
  :min-lein-version "2.0.0"
  :description "Robot Simulator"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein-junit "1.1.2"]]
  :source-paths ["src/clojure"]
  :java-source-paths ["src/java" "test/java"]
  :junit ["test/java"]
  :javac-options ["-Xlint:unchecked"]
  :profiles {:dev {:dependencies [[junit/junit "4.11"]]}}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [net.mikera/core.matrix "0.11.0"]
                 [junit/junit "4.11"]
                 [com.googlecode.efficient-java-matrix-library/ejml "0.23"]])

