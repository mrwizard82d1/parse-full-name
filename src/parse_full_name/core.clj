(ns parse-full-name.core
  (:require [parse-full-name.parser :as parser]
            [parse-full-name.tokenizer :as tokenizer]))


(defn parse [full-name]
  (let [full-name-tokenizer (tokenizer/make full-name)]
    (parser/parse full-name-tokenizer)))

(defn first-name [parse-result]
  "Return the parsed first name."
  (apply subs (:full-name parse-result) (:first-name parse-result)))

(defn last-name [parse-result]
  "Return the parsed last name."
  (apply subs (:full-name parse-result) (:last-name parse-result)))

(defn middle-initial [parse-result]
  "Return the parsed middle initial."
  (apply subs (:full-name parse-result) (:middle-initial parse-result)))

