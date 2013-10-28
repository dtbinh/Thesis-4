(ns jsim.robot
  (:require (core.matrix :refer :all)))


(defprotocol Drive
  (move [])
  (move' []))

(deftype robot [])

