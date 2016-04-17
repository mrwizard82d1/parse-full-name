(ns parse-full-name.parser)

;; <full-name> :: <last-name><comma><family-names>
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
;;                          <middle-initial-letter><period>

(defn next-token [state]
  (get (:tokens state) (:next-token state)))

(defn last-name [state]
  (if-let [last-name (:name (next-token state))]
    (assoc state :last-name last-name :next-token (inc (:next-token state)))))

(defn comma [state]
  (if-let [last-name (:comma (next-token state))]
    (assoc state :next-token (inc (:next-token state)))))

(defn family-names [state]
  (let [remaining-tokens (subvec (:tokens state) (:next-token state))
        family-names-seq (take-while :name remaining-tokens)]
    (when (not (empty? family-names-seq)) 
      (assoc state
             :family-names (map :name family-names-seq)
             :next-token (+ (:next-token state) (count family-names-seq))))))

(defn full-name [state]
  (when-let [last-name-state (last-name state)]
    (when-let [comma-state (comma last-name-state)]
      (family-names comma-state))))

(defn parse [tokens]
  (full-name {:tokens tokens :next-token 0}))

