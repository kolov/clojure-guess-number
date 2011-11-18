(ns pwc.core
  (:gen-class :extends javax.servlet.http.HttpServlet)
  (:use compojure.core
        ring.util.servlet)
  (:require [compojure.route :as route]))

(defroutes hello
  (GET "/" [] "<h1>Hello World Wide Web!</h1>")
  (GET "/user/:id" [id]
    (str "<h1>Hello user " id "</h1>"))
  (route/not-found "Page not found"))

(defservice hello)