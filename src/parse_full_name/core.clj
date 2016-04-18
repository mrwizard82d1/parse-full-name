(ns parse-full-name.core
  (:require [parse-full-name.parser :as parser]
            [parse-full-name.tokenizer :as tokenizer]))


(defn parse [full-name]
  (->> full-name
       tokenizer/tokens
       parser/parse))

(defn first-name [parse-result]
  "Return the parsed first name."
  (apply str (interpose " " (:given-names parse-result))))

(defn last-name [parse-result]
  "Return the parsed last name."
  (apply str (interpose " " (:surnames parse-result))))

(defn middle-initial [parse-result]
  "Return the parsed middle initial."
  (when-let [middle-initial (:middle-initial parse-result)]
    (apply str (:middle-initial parse-result))))

(defn names [full-name]
  (let [parse-result (parse full-name)
        first-piece {:first-name (first-name parse-result)}
        middle-piece (if-let [middle-initial (middle-initial parse-result)]
                       (assoc first-piece :middle-initial middle-initial)
                       first-piece)]
    (assoc middle-piece :last-name (last-name parse-result))))
