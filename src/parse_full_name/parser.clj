(ns parse-full-name.parser)

;; <full-name> :: <surnames>><family-names>
;; <surnames> :: <NAME><surnames> |
;;               <NAME><COMMA>
;; <given-names> :: <NAME><given-names> |
;;                  <NAME>
;;
;; <NAME> :: <name-letter><name-letters> |
;;           <name-letter>

(defn next-token [state]
  (get (:tokens state) (:next-token state)))

(defn surnames [state]
  (let [remaining-tokens (subvec (:tokens state) (:next-token state))
        surnames-seq (take-while :name remaining-tokens)]
    (when (not (empty? surnames-seq)) 
      (assoc state
             :surnames (map :name surnames-seq)
             :next-token (+ (:next-token state) (count surnames-seq))))))

(defn comma [state]
  (if-let [surnames (:comma (next-token state))]
    (assoc state :next-token (inc (:next-token state)))))

(defn given-names [state]
  (let [remaining-tokens (subvec (:tokens state) (:next-token state))
        given-names-seq (take-while :name remaining-tokens)]
    (when (not (empty? given-names-seq)) 
      (assoc state
             :given-names (map :name given-names-seq)
             :next-token (+ (:next-token state) (count given-names-seq))))))

(defn full-name [state]
  (when-let [surnames-state (surnames state)]
    (when-let [comma-state (comma surnames-state)]
      (when-let [given-name-state (given-names comma-state)]
        (if (and (> (count (:given-names given-name-state)) 1)
                 (= 1 (count (last (:given-names given-name-state)))))
          (assoc given-name-state
                 :given-names (butlast (:given-names given-name-state))
                 :middle-initial (last (:given-names given-name-state)))
          given-name-state)))))

(defn parse [tokens]
  (full-name {:tokens (vec (filter (complement :whitespace) tokens)) :next-token 0}))

