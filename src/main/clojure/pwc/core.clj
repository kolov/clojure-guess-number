(ns pwc.core
  (:gen-class :extends javax.servlet.http.HttpServlet)
  (:use compojure.core ring.util.servlet ring.middleware.params, ring.middleware.session)
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.adapter.jetty :as ring]
            [hiccup.core :as h]
            [hiccup.form-helpers :as fh]
            ))

(defn layout [title & body]
  (h/html
    [:head [:title title]]
    [:body [:h1.header title] body]))

(defn say-hello [name]
  (layout "Welcome Page"
    [:h3 (str "Hello " name)]))

(defn render-form [txt]
  (h/html
    (fh/form-to [:post "/guess"]
      txt
      [:br ]
      (fh/text-field :guess )
      (fh/submit-button "Guess")))
  )

(defn handle-form [guess-str secret]
  (let [guess (read-string guess-str)]
    (if (= secret guess)
      (str "You guessed it!")
      (render-form
        (if (> guess secret) (str guess " is too high. Try lower!") (str guess " is too low. Try higher!"))
        )
      )
    )
  )

(defn view [session]
  (h/html [:h1 (str "session: " session)]))

(defroutes guess-routes
  (GET "/" [] {:body (render-form "Guess my number between 1 and 100")
               :session {:secret (+ 1 (rand-int 100))}
               :headers {"Content-Type" "text/html"}
               })
  (POST "/guess" {params :params session :session} (handle-form (params :guess) (session :secret )))
  (GET "/view" {session :session} (view session))
  )

(wrap! guess-routes :session )

(defservice guess-routes)

(def application (handler/site guess-routes))

(defn start [port]
  (ring/run-jetty (var application) {:port port :join? false}))

#_ ("Uncomment to run")
(start 9900)
