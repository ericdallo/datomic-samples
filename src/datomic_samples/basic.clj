(ns datomic-samples.basic
  (:require [datomic.api :as d]))

; Creating database
(def db-uri "datomic:free://localhost:4334/datomic-samples.basic?password=datomic")
(d/create-database db-uri)

(def conn (d/connect db-uri))

; Creating schema
@(d/transact conn [{:db/ident :person/name
                    :db/valueType :db.type/string
                    :db/cardinality :db.cardinality/one
                    :db/doc "The person s name"}
                  {:db/ident :person/email
                   :db/valueType :db.type/string
                   :db/cardinality :db.cardinality/one
                   :db/doc "The person s email"}
                   {:db/ident :person/age
                    :db/valueType :db.type/long
                    :db/cardinality :db.cardinality/one
                    :db/doc "The person s age"}])

; Add data
@(d/transact conn [{:person/name "Greg"
                    :person/email "greg.ator@sample.com"
                    :person/age 24}
                   {:person/name "John"
                    :person/email "john.lennon@beatles.com"
                    :person/age 79}
                   {:person/name "Paul"
                    :person/email "paul.mccartney@beatles.com"
                    :person/age 77}])

; Query data
(def db (d/db conn))

(def all-people
  (d/q
   '[:find ?e
    :where [?e :person/name]]
   db))

(def all-people-names
  (d/q
   '[:find ?name
     :where [_ :person/name ?name]]
   db))

(def people-names-older-than-50
  (d/q
   '[:find ?name
     :where [?e :person/name ?name]
            [?e :person/age ?age]
            [(> ?age 50)]]
   db))
