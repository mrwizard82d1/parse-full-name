(ns parse-full-name.tokenizer-test
  (:require [clojure.test :refer :all]
            [parse-full-name.tokenizer :as tokenizer]))

(deftest next-token-test
  (testing "Single token"
    (nil? (seq (tokenizer/tokens "")))
    (are [c s] (= [{c s}] (tokenizer/tokens s))
      :name "occludo"
      :name "calumnias'offeritis"
      :name "pernicitam-polimus"
      :name "\u03b4"                    ; Greek lower case delta
      :comma ","
      :period "."
      :whitespace " "
      :whitespace "\t"))
  (testing "Many tokens"
    (are [e s] (= e (tokenizer/tokens s))
      [{:name "Jones"} {:comma ","} {:name "Lawrence"} {:whitespace " "} {:name "A"} {:period "."}]
      "Jones,Lawrence A."
      [{:name "O'Neal"} {:comma ","} {:whitespace " "} {:name "Kevin"}] "O'Neal, Kevin"
      [{:name "Hoare"} {:comma ","} {:whitespace " "}
       {:name "C"} {:whitespace " "} {:name "A"} {:whitespace "\t"} {:name "R"}] "Hoare, C A\tR"
      [{:name "Orthographiarum-Parent"} {:comma ","} {:whitespace " "}
       {:name "Do"} {:whitespace " "}
       {:name "Declamatis"}] "Orthographiarum-Parent, Do Declamatis")))
