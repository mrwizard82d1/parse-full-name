(ns parse-full-name.tokenizer-test
  (:require [clojure.test :refer :all]
            [parse-full-name.tokenizer :as tokenizer]))

(deftest next-token-test
  (testing "Single token"
    (is (= [0 9] (tokenizer/next-token {:text "umbilicus"
                                        :next-char 0
                                        :next-token 0
                                        :tokens []}))))
  (is (= [1 10] (tokenizer/next-token {:text " umbilicus"
                                        :next-char 0
                                        :next-token 0
                                        :tokens []}))))


