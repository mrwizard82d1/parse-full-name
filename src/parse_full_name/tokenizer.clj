(ns parse-full-name.tokenizer)

(defn make [full-name]
  {:text full-name :next-char 0 :next-token 0 :tokens []})

(defn characterize [ch]
        (cond (= ch \,) :comma
              (= ch \.) :period
              (Character/isWhitespace ch) :whitespace
              (or (= ch \')
                  (= ch \-)
                  (Character/isLetter ch)) :name))

(defn characterize-token [token-chars]
  (hash-map (characterize (first token-chars)) (apply str token-chars)))

(defn tokens [full-name]
  (->> full-name
   (partition-by characterize)
   (map characterize-token)))
