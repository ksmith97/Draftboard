(ns draftboard.pages.create-draft
    (:require [reagent.core :as reagent :refer [atom]]
              [clojure.string :as string :refer [blank?]]))

(defn create-draft-page []
    (let [form (atom {})]
        (fn []
            [:div#create-draft
                [:h1 "Create a Draft"]
                [:form
                    [:div.form-group
                        [:label "Draft Name"
                            [:input.form-control {:placeholder "Enter draft name"
                                                  :on-change #(do 
                                                                (swap! form assoc :name (-> % .-target .-value))
                                                                ;We explicitly return nil to avoid preventing event propogation.
                                                                nil)}]]]
                    [:div.form-group
                        [:label "Password"
                            [:input.form-control {:placeholder "Enter password here"
                                                  :on-change #(do 
                                                                (swap! form assoc :password (-> % .-target .-value))
                                                                ;We explicitly return nil to avoid preventing event propogation.
                                                                nil)}]]]
                    [:div
                        [:a.btn.btn-default {:href "#/"} "Cancel"]
                        [:a.btn.btn-primary.pull-right {:href (str "#/board/" (get @form :name))
                                                        :on-click #(do (println @form) (when (blank? (get @form :name))
                                                                         (do 
                                                                            (js/alert "The name must not be blank") 
                                                                            ;Return false to prevent event propogation
                                                                            false)))} 
                                                        "Start Draft!"]]]])))
