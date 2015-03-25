(ns draftboard.core
    (:require [reagent.core :as reagent :refer [atom]]
              [reagent.session :as session]
              [secretary.core :as secretary :include-macros true]
              [goog.events :as events]
              [goog.history.EventType :as EventType]
              [cljsjs.react :as react]
              [draftboard.pages.welcome :refer [welcome-page]]
              [draftboard.pages.draftboard :refer [draftboard-page]]
              [draftboard.pages.create-draft :refer [create-draft-page]])
    (:import goog.History))

;; -------------------------
;; Views

(defn about-page []
  [:div [:h2 "About draftboard"]
   [:div [:a {:href "#/"} "go to the home page"]]])

(defn current-page []
  [:div (let [page (session/get :current-page)] 
        (if (coll? page)
            page
            (vector page)))])
;; -------------------------
;; Routes
(secretary/set-config! :prefix "#")

(secretary/defroute "/" []
  (session/put! :current-page #'welcome-page))

(secretary/defroute "/create" []
  (session/put! :current-page #'create-draft-page))

(secretary/defroute "/board/:id" [id]
  (session/put! :current-page [#'draftboard-page id]))

(secretary/defroute "/about" []
  (session/put! :current-page #'about-page))

;; -------------------------
;; History
;; must be called after routes have been defined
(defn hook-browser-navigation! []
  (doto (History.)
    (events/listen
     EventType/NAVIGATE
     (fn [event]
       (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

;; -------------------------
;; Initialize app
(defn mount-root []
  (reagent/render [current-page] (.getElementById js/document "draftboard")))

(defn init! []
  (hook-browser-navigation!)
  (mount-root))
