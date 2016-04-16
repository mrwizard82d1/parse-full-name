(ns parse-full-name.tokenizer)

(defn make [full-name]
  {:text full-name :next-char 0 :next-token 0 :tokens []})

(defn consume-whitespace [text start-at]
  (let [candidate-indices (range start-at (count text))
        whitespace-start-indices (drop-while
                                  #(Character/isWhitespace (.charAt text %))
                                  candidate-indices)]
    (first whitespace-start-indices)))

(defn consume-letters [text start-at]
  (let [candidate-indices (range start-at (count text))
        letter-indices (take-while
                        #(Character/isLetter (.charAt text %))
                        candidate-indices)]
    (inc (last letter-indices))))

(defn next-token [state]
  (let [start (consume-whitespace (:text state) (:next-char state))
        end (consume-letters (:text state) start)]
    [start end]))

(defn text [state]
  (:text state))

(defn next-char [state]
  (:next-char state))
