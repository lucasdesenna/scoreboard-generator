(ns scoreboard-generator.test-resources
  (:require [clojure.test :refer :all]
            [clojure.java.io :as io]
            [flatland.ordered.map :as fl]
            [scoreboard-generator.core :refer :all]
            [scoreboard-generator.controller :as controller]))

(defonce basic-input (io/file "test/test_inputs/basic_input.txt"))

(defonce basic-invitations '(#scoreboard_generator.invitation.Invitation{:inviter 1, :invitee 2}
                             #scoreboard_generator.invitation.Invitation{:inviter 1, :invitee 3}
                             #scoreboard_generator.invitation.Invitation{:inviter 3, :invitee 4}
                             #scoreboard_generator.invitation.Invitation{:inviter 2, :invitee 4}
                             #scoreboard_generator.invitation.Invitation{:inviter 4, :invitee 5}
                             #scoreboard_generator.invitation.Invitation{:inviter 4, :invitee 6}))

(defonce basic-node-map {1 {:children '(2 3)},
                         2 {:parent 1},
                         3 {:parent 1, :children '(4)},
                         4 {:parent 3}})

(defonce basic-scoreboard (fl/ordered-map 1 2.5 3 1 2 0 4 0))

(defonce basic-json "{\"1\":2.5,\"3\":1,\"2\":0,\"4\":0}")

(defonce standard-input (io/file "test/test_inputs/standard_input.txt"))

(defonce standard-invitations '(#scoreboard_generator.invitation.Invitation{:inviter 1, :invitee 2} 
                                #scoreboard_generator.invitation.Invitation{:inviter 2, :invitee 3} 
                                #scoreboard_generator.invitation.Invitation{:inviter 3, :invitee 4} 
                                #scoreboard_generator.invitation.Invitation{:inviter 4, :invitee 5} 
                                #scoreboard_generator.invitation.Invitation{:inviter 5, :invitee 6} 
                                #scoreboard_generator.invitation.Invitation{:inviter 6, :invitee 7} 
                                #scoreboard_generator.invitation.Invitation{:inviter 7, :invitee 8} 
                                #scoreboard_generator.invitation.Invitation{:inviter 8, :invitee 9} 
                                #scoreboard_generator.invitation.Invitation{:inviter 9, :invitee 10} 
                                #scoreboard_generator.invitation.Invitation{:inviter 10, :invitee 11} 
                                #scoreboard_generator.invitation.Invitation{:inviter 11, :invitee 12} 
                                #scoreboard_generator.invitation.Invitation{:inviter 12, :invitee 13} 
                                #scoreboard_generator.invitation.Invitation{:inviter 13, :invitee 14} 
                                #scoreboard_generator.invitation.Invitation{:inviter 14, :invitee 15} 
                                #scoreboard_generator.invitation.Invitation{:inviter 15, :invitee 16} 
                                #scoreboard_generator.invitation.Invitation{:inviter 16, :invitee 17} 
                                #scoreboard_generator.invitation.Invitation{:inviter 17, :invitee 18} 
                                #scoreboard_generator.invitation.Invitation{:inviter 18, :invitee 19} 
                                #scoreboard_generator.invitation.Invitation{:inviter 19, :invitee 20} 
                                #scoreboard_generator.invitation.Invitation{:inviter 20, :invitee 21} 
                                #scoreboard_generator.invitation.Invitation{:inviter 21, :invitee 22} 
                                #scoreboard_generator.invitation.Invitation{:inviter 22, :invitee 23} 
                                #scoreboard_generator.invitation.Invitation{:inviter 23, :invitee 24} 
                                #scoreboard_generator.invitation.Invitation{:inviter 24, :invitee 25} 
                                #scoreboard_generator.invitation.Invitation{:inviter 25, :invitee 26} 
                                #scoreboard_generator.invitation.Invitation{:inviter 26, :invitee 27} 
                                #scoreboard_generator.invitation.Invitation{:inviter 27, :invitee 28} 
                                #scoreboard_generator.invitation.Invitation{:inviter 28, :invitee 29} 
                                #scoreboard_generator.invitation.Invitation{:inviter 29, :invitee 30} 
                                #scoreboard_generator.invitation.Invitation{:inviter 30, :invitee 31} 
                                #scoreboard_generator.invitation.Invitation{:inviter 31, :invitee 32} 
                                #scoreboard_generator.invitation.Invitation{:inviter 32, :invitee 33} 
                                #scoreboard_generator.invitation.Invitation{:inviter 33, :invitee 34} 
                                #scoreboard_generator.invitation.Invitation{:inviter 34, :invitee 35} 
                                #scoreboard_generator.invitation.Invitation{:inviter 35, :invitee 36} 
                                #scoreboard_generator.invitation.Invitation{:inviter 36, :invitee 37} 
                                #scoreboard_generator.invitation.Invitation{:inviter 37, :invitee 38} 
                                #scoreboard_generator.invitation.Invitation{:inviter 38, :invitee 39} 
                                #scoreboard_generator.invitation.Invitation{:inviter 39, :invitee 40} 
                                #scoreboard_generator.invitation.Invitation{:inviter 40, :invitee 41} 
                                #scoreboard_generator.invitation.Invitation{:inviter 41, :invitee 42} 
                                #scoreboard_generator.invitation.Invitation{:inviter 42, :invitee 43} 
                                #scoreboard_generator.invitation.Invitation{:inviter 43, :invitee 44} 
                                #scoreboard_generator.invitation.Invitation{:inviter 44, :invitee 45} 
                                #scoreboard_generator.invitation.Invitation{:inviter 45, :invitee 46} 
                                #scoreboard_generator.invitation.Invitation{:inviter 46, :invitee 47} 
                                #scoreboard_generator.invitation.Invitation{:inviter 47, :invitee 48} 
                                #scoreboard_generator.invitation.Invitation{:inviter 48, :invitee 49} 
                                #scoreboard_generator.invitation.Invitation{:inviter 49, :invitee 50} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 51} 
                                #scoreboard_generator.invitation.Invitation{:inviter 51, :invitee 101} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 52} 
                                #scoreboard_generator.invitation.Invitation{:inviter 52, :invitee 102} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 53} 
                                #scoreboard_generator.invitation.Invitation{:inviter 53, :invitee 103} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 54} 
                                #scoreboard_generator.invitation.Invitation{:inviter 54, :invitee 104} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 55} 
                                #scoreboard_generator.invitation.Invitation{:inviter 55, :invitee 105} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 56} 
                                #scoreboard_generator.invitation.Invitation{:inviter 56, :invitee 106} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 57} 
                                #scoreboard_generator.invitation.Invitation{:inviter 57, :invitee 107} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 58} 
                                #scoreboard_generator.invitation.Invitation{:inviter 58, :invitee 108} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 59} 
                                #scoreboard_generator.invitation.Invitation{:inviter 59, :invitee 109} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 60} 
                                #scoreboard_generator.invitation.Invitation{:inviter 60, :invitee 110} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 61} 
                                #scoreboard_generator.invitation.Invitation{:inviter 61, :invitee 111} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 62} 
                                #scoreboard_generator.invitation.Invitation{:inviter 62, :invitee 112} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 63} 
                                #scoreboard_generator.invitation.Invitation{:inviter 63, :invitee 113} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 64} 
                                #scoreboard_generator.invitation.Invitation{:inviter 64, :invitee 114} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 65} 
                                #scoreboard_generator.invitation.Invitation{:inviter 65, :invitee 115} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 66} 
                                #scoreboard_generator.invitation.Invitation{:inviter 66, :invitee 116} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 67} 
                                #scoreboard_generator.invitation.Invitation{:inviter 67, :invitee 117} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 68} 
                                #scoreboard_generator.invitation.Invitation{:inviter 68, :invitee 118} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 69} 
                                #scoreboard_generator.invitation.Invitation{:inviter 69, :invitee 119} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 70} 
                                #scoreboard_generator.invitation.Invitation{:inviter 70, :invitee 120} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 71} 
                                #scoreboard_generator.invitation.Invitation{:inviter 71, :invitee 121} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 72} 
                                #scoreboard_generator.invitation.Invitation{:inviter 72, :invitee 122} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 73} 
                                #scoreboard_generator.invitation.Invitation{:inviter 73, :invitee 123} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 74} 
                                #scoreboard_generator.invitation.Invitation{:inviter 74, :invitee 124} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 75} 
                                #scoreboard_generator.invitation.Invitation{:inviter 75, :invitee 125} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 76} 
                                #scoreboard_generator.invitation.Invitation{:inviter 76, :invitee 126} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 77} 
                                #scoreboard_generator.invitation.Invitation{:inviter 77, :invitee 127} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 78} 
                                #scoreboard_generator.invitation.Invitation{:inviter 78, :invitee 128} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 79} 
                                #scoreboard_generator.invitation.Invitation{:inviter 79, :invitee 129} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 80} 
                                #scoreboard_generator.invitation.Invitation{:inviter 80, :invitee 130} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 81} 
                                #scoreboard_generator.invitation.Invitation{:inviter 81, :invitee 131} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 82} 
                                #scoreboard_generator.invitation.Invitation{:inviter 82, :invitee 132} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 83} 
                                #scoreboard_generator.invitation.Invitation{:inviter 83, :invitee 133} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 84} 
                                #scoreboard_generator.invitation.Invitation{:inviter 84, :invitee 134} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 85} 
                                #scoreboard_generator.invitation.Invitation{:inviter 85, :invitee 135} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 86} 
                                #scoreboard_generator.invitation.Invitation{:inviter 86, :invitee 136} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 87} 
                                #scoreboard_generator.invitation.Invitation{:inviter 87, :invitee 137} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 88} 
                                #scoreboard_generator.invitation.Invitation{:inviter 88, :invitee 138} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 89} 
                                #scoreboard_generator.invitation.Invitation{:inviter 89, :invitee 139} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 90} 
                                #scoreboard_generator.invitation.Invitation{:inviter 90, :invitee 140} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 91} 
                                #scoreboard_generator.invitation.Invitation{:inviter 91, :invitee 141} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 92} 
                                #scoreboard_generator.invitation.Invitation{:inviter 92, :invitee 142} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 93} 
                                #scoreboard_generator.invitation.Invitation{:inviter 93, :invitee 143} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 94} 
                                #scoreboard_generator.invitation.Invitation{:inviter 94, :invitee 144} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 95} 
                                #scoreboard_generator.invitation.Invitation{:inviter 95, :invitee 145} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 96} 
                                #scoreboard_generator.invitation.Invitation{:inviter 96, :invitee 146} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 97} 
                                #scoreboard_generator.invitation.Invitation{:inviter 97, :invitee 147} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 98} 
                                #scoreboard_generator.invitation.Invitation{:inviter 98, :invitee 148} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 99} 
                                #scoreboard_generator.invitation.Invitation{:inviter 99, :invitee 149} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 100} 
                                #scoreboard_generator.invitation.Invitation{:inviter 100, :invitee 150} 
                                #scoreboard_generator.invitation.Invitation{:inviter 69, :invitee 70} 
                                #scoreboard_generator.invitation.Invitation{:inviter 70, :invitee 53} 
                                #scoreboard_generator.invitation.Invitation{:inviter 52, :invitee 54} 
                                #scoreboard_generator.invitation.Invitation{:inviter 59, :invitee 21} 
                                #scoreboard_generator.invitation.Invitation{:inviter 86, :invitee 9} 
                                #scoreboard_generator.invitation.Invitation{:inviter 94, :invitee 43} 
                                #scoreboard_generator.invitation.Invitation{:inviter 59, :invitee 96} 
                                #scoreboard_generator.invitation.Invitation{:inviter 15, :invitee 69} 
                                #scoreboard_generator.invitation.Invitation{:inviter 15, :invitee 40} 
                                #scoreboard_generator.invitation.Invitation{:inviter 93, :invitee 2} 
                                #scoreboard_generator.invitation.Invitation{:inviter 77, :invitee 52} 
                                #scoreboard_generator.invitation.Invitation{:inviter 74, :invitee 66} 
                                #scoreboard_generator.invitation.Invitation{:inviter 39, :invitee 81} 
                                #scoreboard_generator.invitation.Invitation{:inviter 8, :invitee 18} 
                                #scoreboard_generator.invitation.Invitation{:inviter 61, :invitee 68} 
                                #scoreboard_generator.invitation.Invitation{:inviter 63, :invitee 15} 
                                #scoreboard_generator.invitation.Invitation{:inviter 51, :invitee 50} 
                                #scoreboard_generator.invitation.Invitation{:inviter 93, :invitee 77} 
                                #scoreboard_generator.invitation.Invitation{:inviter 28, :invitee 91} 
                                #scoreboard_generator.invitation.Invitation{:inviter 23, :invitee 22} 
                                #scoreboard_generator.invitation.Invitation{:inviter 42, :invitee 1} 
                                #scoreboard_generator.invitation.Invitation{:inviter 95, :invitee 96} 
                                #scoreboard_generator.invitation.Invitation{:inviter 1, :invitee 46} 
                                #scoreboard_generator.invitation.Invitation{:inviter 10, :invitee 49} 
                                #scoreboard_generator.invitation.Invitation{:inviter 34, :invitee 26} 
                                #scoreboard_generator.invitation.Invitation{:inviter 80, :invitee 21} 
                                #scoreboard_generator.invitation.Invitation{:inviter 88, :invitee 94} 
                                #scoreboard_generator.invitation.Invitation{:inviter 11, :invitee 10} 
                                #scoreboard_generator.invitation.Invitation{:inviter 80, :invitee 24} 
                                #scoreboard_generator.invitation.Invitation{:inviter 10, :invitee 100} 
                                #scoreboard_generator.invitation.Invitation{:inviter 20, :invitee 110} 
                                #scoreboard_generator.invitation.Invitation{:inviter 30, :invitee 120} 
                                #scoreboard_generator.invitation.Invitation{:inviter 30, :invitee 130} 
                                #scoreboard_generator.invitation.Invitation{:inviter 40, :invitee 140} 
                                #scoreboard_generator.invitation.Invitation{:inviter 50, :invitee 150} 
                                #scoreboard_generator.invitation.Invitation{:inviter 60, :invitee 160} 
                                #scoreboard_generator.invitation.Invitation{:inviter 80, :invitee 100} 
                                #scoreboard_generator.invitation.Invitation{:inviter 90, :invitee 100} 
                                #scoreboard_generator.invitation.Invitation{:inviter 56, :invitee 100} 
                                #scoreboard_generator.invitation.Invitation{:inviter 12, :invitee 10} 
                                #scoreboard_generator.invitation.Invitation{:inviter 10, :invitee 12} 
                                #scoreboard_generator.invitation.Invitation{:inviter 100, :invitee 56} 
                                #scoreboard_generator.invitation.Invitation{:inviter 100, :invitee 90} 
                                #scoreboard_generator.invitation.Invitation{:inviter 40, :invitee 140} 
                                #scoreboard_generator.invitation.Invitation{:inviter 10, :invitee 150} 
                                #scoreboard_generator.invitation.Invitation{:inviter 11, :invitee 180} 
                                #scoreboard_generator.invitation.Invitation{:inviter 10, :invitee 190} 
                                #scoreboard_generator.invitation.Invitation{:inviter 24, :invitee 48} 
                                #scoreboard_generator.invitation.Invitation{:inviter 25, :invitee 49} 
                                #scoreboard_generator.invitation.Invitation{:inviter 16, :invitee 48}))

(defonce standard-node-map {1 {:children '(2)},
                            2 {:parent 1, :children '(3)},
                            3 {:parent 2, :children '(4)},
                            4 {:parent 3, :children '(5)},
                            5 {:parent 4, :children '(6)},
                            6 {:parent 5, :children '(7)},
                            7 {:parent 6, :children '(8)},
                            8 {:parent 7, :children '(9)},
                            9 {:parent 8, :children '(10)},
                            10 {:parent 9, :children '(11)},
                            11 {:parent 10, :children '(12)},
                            12 {:parent 11, :children '(13)},
                            13 {:parent 12, :children '(14)},
                            14 {:parent 13, :children '(15)},
                            15 {:parent 14, :children '(16)},
                            16 {:parent 15, :children '(17)},
                            17 {:parent 16, :children '(18)},
                            18 {:parent 17, :children '(19)},
                            19 {:parent 18, :children '(20)},
                            20 {:parent 19, :children '(21)},
                            21 {:parent 20, :children '(22)},
                            22 {:parent 21, :children '(23)},
                            23 {:parent 22, :children '(24)},
                            24 {:parent 23, :children '(25)},
                            25 {:parent 24, :children '(26)},
                            26 {:parent 25, :children '(27)},
                            27 {:parent 26, :children '(28)},
                            28 {:parent 27, :children '(29)},
                            29 {:parent 28, :children '(30)},
                            30 {:parent 29, :children '(31)},
                            31 {:parent 30, :children '(32)},
                            32 {:parent 31, :children '(33)},
                            33 {:parent 32, :children '(34)},
                            34 {:parent 33, :children '(35)},
                            35 {:parent 34, :children '(36)},
                            36 {:parent 35, :children '(37)},
                            37 {:parent 36, :children '(38)},
                            38 {:parent 37, :children '(39)},
                            39 {:parent 38, :children '(40)},
                            40 {:parent 39, :children '(41)},
                            41 {:parent 40, :children '(42)},
                            42 {:parent 41, :children '(43)},
                            43 {:parent 42, :children '(44)},
                            44 {:parent 43, :children '(45)},
                            45 {:parent 44, :children '(46)},
                            46 {:parent 45, :children '(47)},
                            47 {:parent 46, :children '(48)},
                            48 {:parent 47, :children '(49)},
                            49 {:parent 48, :children '(50)},
                            50 {:parent 49, :children '(51 52 53 54 55 56 57 58 59 60 61 62 63 64 65 66 67 68 69 70 71 72 73 74 75 76 77 78 79 80 81 82 83 84 85 86 87 88 89 90 91 92 93 94 95 96 97 98 99 100)},
                            51 {:parent 50},
                            52 {:parent 50},
                            53 {:parent 50},
                            54 {:parent 50},
                            55 {:parent 50},
                            56 {:parent 50},
                            57 {:parent 50},
                            58 {:parent 50},
                            59 {:parent 50},
                            60 {:parent 50},
                            61 {:parent 50},
                            62 {:parent 50},
                            63 {:parent 50},
                            64 {:parent 50},
                            65 {:parent 50},
                            66 {:parent 50},
                            67 {:parent 50},
                            68 {:parent 50},
                            69 {:parent 50},
                            70 {:parent 50},
                            71 {:parent 50},
                            72 {:parent 50},
                            73 {:parent 50},
                            74 {:parent 50},
                            75 {:parent 50},
                            76 {:parent 50},
                            77 {:parent 50},
                            78 {:parent 50},
                            79 {:parent 50},
                            80 {:parent 50},
                            81 {:parent 50},
                            82 {:parent 50},
                            83 {:parent 50},
                            84 {:parent 50},
                            85 {:parent 50},
                            86 {:parent 50},
                            87 {:parent 50},
                            88 {:parent 50},
                            89 {:parent 50},
                            90 {:parent 50},
                            91 {:parent 50},
                            92 {:parent 50},
                            93 {:parent 50},
                            94 {:parent 50},
                            95 {:parent 50},
                            96 {:parent 50},
                            97 {:parent 50},
                            98 {:parent 50},
                            99 {:parent 50},
                            100 {:parent 50}})

(defonce standard-scoreboard (fl/ordered-map 50 50 49 26.0 48 14.0 47 8.0 46 5.0 45 3.5 44 2.75 43 2.375 42 2.1875 41 2.09375 40 2.046875 39 2.0234375 38 2.01171875 37 2.005859375 36 2.0029296875 35 2.00146484375 34 2.000732421875 33 2.0003662109375 32 2.00018310546875 31 2.000091552734375 30 2.0000457763671875 29 2.0000228881835938 28 2.000011444091797 27 2.0000057220458984 26 2.000002861022949 25 2.0000014305114746 24 2.0000007152557373 23 2.0000003576278687 22 2.0000001788139343 21 2.000000089406967 20 2.0000000447034836 19 2.000000022351742 18 2.000000011175871 17 2.0000000055879354 16 2.0000000027939677 15 2.000000001396984 14 2.000000000698492 13 2.000000000349246 12 2.000000000174623 11 2.0000000000873115 10 2.0000000000436557 9 2.000000000021828 8 2.000000000010914 7 2.000000000005457 6 2.0000000000027285 5 2.0000000000013642 4 2.000000000000682 3 2.000000000000341 2 2.0000000000001705 1 2.0000000000000853 51 0 52 0 53 0 54 0 55 0 56 0 57 0 58 0 59 0 60 0 61 0 62 0 63 0 64 0 65 0 66 0 67 0 68 0 69 0 70 0 71 0 72 0 73 0 74 0 75 0 76 0 77 0 78 0 79 0 80 0 81 0 82 0 83 0 84 0 85 0 86 0 87 0 88 0 89 0 90 0 91 0 92 0 93 0 94 0 95 0 96 0 97 0 98 0 99 0 100 0))

(defonce dirty-input (io/file "test/test_inputs/dirty_input.txt"))

(defonce long-input (io/file "test/test_inputs/long_input.txt"))

(defonce invalid-input (io/file "test/test_inputs/invalid_input.txt"))

(defonce corrupt-input (io/file "test/test_inputs/corrupt_input.txt"))