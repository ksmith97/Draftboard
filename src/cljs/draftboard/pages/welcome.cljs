(ns draftboard.pages.welcome)

(defn welcome-page []
  [:div#welcome [:h1 "Welcome to the draft!"]
   [:div [:a.btn.btn-success {:href "#/create"} "Create a new draft"]]
   [:div [:a.btn.btn-primary {:href "#/join"} "Join an existing draft"]]
   [:div [:a.btn.btn-info {:href "#/about"} "About Draftboard"]]])

