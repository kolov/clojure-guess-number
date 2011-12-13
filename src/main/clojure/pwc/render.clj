(ns pwc.render
  (:require [net.cgrand.enlive-html :as html])
  )

(html/deftemplate guess-page "pwc/guess-page.html"
  [ctxt]
  [:p#message ] (html/content (:message ctxt))
  )

(html/deftemplate success-page "pwc/success-page.html"
  [ctxt]
  )


(defn render-guess-page [msg] (guess-page {:message msg}))
(defn render-success-page [] (success-page {}))


