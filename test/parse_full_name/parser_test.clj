(ns parse-full-name.parser-test
  (:require [clojure.test :refer :all]
            [parse-full-name.parser :as parser]))

(deftest parser-test
  (testing "empty input"
    (is (nil? (parser/parse (lazy-seq nil)))))
  (testing "valid input"
    (let [tokens
          [{:name "Jones"} {:comma ","} {:name "Lawrence"} {:whitespace " "} {:name "A"}]]
      (is (= {:tokens tokens :next-token 2
              :surnames "Jones" :first-name "Lawrence" :middle-initial "A"}
             (parser/parse tokens)))))
  (let [tokens [{:name "coris"} {:comma ","} {:name "nulli"}
                {:whitespace " "} {:name "potentatus"}]]
    (testing "next-token"
      (are [expected next-index] (= expected
                                    (parser/next-token {:tokens tokens :next-token next-index}))
        {:name "coris"} 0
        {:comma ","} 1
        {:name "potentatus"} 4
        nil 5))
    (testing "surnames"
      (are [expected-text next-index] (= expected-text
                                         (:surnames (parser/surnames
                                                      {:tokens tokens :next-token next-index})))
        ["nulli"] 2
        nil 3)
      (are [expected-names] (= expected-names
                               (:surnames (parser/surnames
                                              {:tokens
                                               (vec (map #(hash-map :name %) expected-names))
                                               :next-token 0})))
        ["nulli"]
        ["null" "silva" "capo"])
      (testing "increments next token index if last name found"
        (is (= 5 (:next-token (parser/surnames {:tokens tokens :next-token 4}))))))
    (testing "comma"
      (testing "increments next token index if comma found"
        (is (= 2 (:next-token (parser/comma {:tokens tokens :next-token 1})))))))
  (testing "given-names"
    (are [expected-names] (= expected-names
                             (:given-names (parser/given-names
                                            {:tokens
                                             (vec (map #(hash-map :name %) expected-names))
                                             :next-token 0})))
      ["vulnarius"]
      ["vulnarius" "malcat" "inculcas"])
    (is (nil? (parser/given-names {:tokens [{:period "."}] :next-token 0})))
    (is (nil? (parser/given-names {:tokens [{:period "."}] :next-token 1})))))
