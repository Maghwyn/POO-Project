//package rpg.game;
//
//import java.util.concurrent.Callable;
//import java.util.concurrent.ThreadLocalRandom;
//
//class Combat implements Callable<Character>{
//
//    Character guerrierA;
//    Character guerrierB;
//    double healthpoints =100;
//
//    public Combat(Character guerrierA, Character guerrierB){
//        this.guerrierA = guerrierA;
//        this.guerrierB = guerrierB;
//    }
//
//    public Character call() throws Exception{
//
//        while(true){
//            Thread.sleep(ThreadLocalRandom.current().nextInt(200, 500));
//            this.guerrierA.getAttackDamages(guerrierB);
//            Thread.sleep(ThreadLocalRandom.current().nextInt(200, 500));
//            this.guerrierB.getAttackDamages(guerrierA);
//
//            double maxPDV = 100;
//            if(guerrierA.getHealthPoints() <= 0){
//                guerrierB.heal(guerrierB.getHealthPoints()/100*30);
//                if(guerrierB.getHealthPoints() > maxPDV) { guerrierB.setLife(maxPDV); }
//                return guerrierB;
//            }
//            else if(guerrierB.getHealthPoints() <= 0){
//                guerrierA.heal(guerrierA.getHealthPoints()/100*30);
//                if(guerrierA.getHealthPoints() > maxPDV) { guerrierA.setLife(maxPDV); }
//                return guerrierA;
//            }
//        }
//    }
//}
//
