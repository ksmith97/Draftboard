(ns draftboard.pages.draftboard
    (:require-macros [draftboard.debug :refer [dbg]]))

(def positions [:C :1B :2B :3B :SS :OF1 :OF2 :OF3 :UTIL1 :UTIL2 :SP1 :SP2 :RP1 :RP2 :P1 :P2 :P3 :P4])

(defn player-spot [position player]
  [:div.draft-spot
   [:div.draft-position position]
   [:div.draft-player-name]])

(defn player-draft [team]
    (into [:div.draft-team 
           [:div.draft-team-name (get team :name)]]
          (map 
            #(player-spot (name %) (-> team :players %)) 
            positions)))

(defn add-team! [team-name draftboard]
  true
  )

(def nm (atom ""))

(defn add-team-component [draftboard]
  (let [team-name (atom "")]
    (fn []
      (println "Add-team-component")
      [:form.add-team-form {:action "javascript:void(0)"}
       [:input.form-control {:type "text" :value @team-name :on-change #(reset! team-name (-> % .-target .-value))}]
       [:button.btn.btn-primary {:on-click #(when (add-team! @team-name draftboard) (reset! team-name ""))} "Add Team"]])))

(defn draftboard-page [draftboard-name]
    (let [draftboard (atom {:draftboard-name draftboard-name
                            :teams [{:name "Team 1"
                                     :players {}} 
                                    {:name "Team 2"
                                     :players {}}]})]
        (fn [] 
            (println "draftboard-page")
            (into [:div#draftboard
                        [:div#draft-buttons
                         [(add-team-component) draftboard]]] 
                       (concat 
                         ;Generate the list of teams
                         (map #(player-draft %) (:teams @draftboard))
                         )))))

