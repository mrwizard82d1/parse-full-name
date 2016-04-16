(ns parse-full-name.parser
  (:require [parse-full-name.tokenizer :as tokenizer]))

;; <full-name> :: <last-name>,<family-names>
;; <last-name> :: <last-name-letters>
;; <last-name-letters> :: <last-name-letter> |
;;                        <last-name-letter><last-name-letters>
;; <family-names> :: <given-names> |
;;                   <given-names><middle-initial-term>
;; <given-names> :: <given-name> |
;;                  <given-name><given-name>
;; <given-name> :: <given-name-letters>
;; <given-name> :: <given-name-letter> |
;;                 <given-name-letter><given-name-letters>
;; <middle-initial-term> :: <middle-initial-letter> |
;;                          <middle-initial-letter>.




(defn parse [supplied-tokenizer]
  {:full-name "Jones, Lawrence A" :last-name [0 5] :first-name [7 15] :middle-initial [16 17]})

