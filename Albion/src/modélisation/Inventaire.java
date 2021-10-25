/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modélisation;

/**
 * Classe qui gère l'inventaire contenant toutes les ressources avec accesseurs
 * @author bkott
 */
public class Inventaire {
    
    private Integer rF8,rF7,rF6,rF5,rF4,rF3,rF2; // Ressources raffinées de tous les niveaux
    private Integer rB8,rB7,rB6,rB5,rB4,rB3,rB2; // Ressources brutes de tous les niveaux

    public Inventaire() {
        rF2 = rF3 = rF4 = rF5 = rF6 = rF7 = rF8 = rB2 = rB3 = rB4 = rB5 = rB6 = rB7 = rB8 = 0;
    }      
    
    public Integer getrF8() {
        return rF8;
    }

    public void setrF8(Integer rF8) {
        this.rF8 = rF8;
    }

    public Integer getrF7() {
        return rF7;
    }

    public void setrF7(Integer rF7) {
        this.rF7 = rF7;
    }

    public Integer getrF6() {
        return rF6;
    }

    public void setrF6(Integer rF6) {
        this.rF6 = rF6;
    }

    public Integer getrF5() {
        return rF5;
    }

    public void setrF5(Integer rF5) {
        this.rF5 = rF5;
    }

    public Integer getrF4() {
        return rF4;
    }

    public void setrF4(Integer rF4) {
        this.rF4 = rF4;
    }

    public Integer getrF3() {
        return rF3;
    }

    public void setrF3(Integer rF3) {
        this.rF3 = rF3;
    }

    public Integer getrF2() {
        return rF2;
    }

    public void setrF2(Integer rF2) {
        this.rF2 = rF2;
    }

    public Integer getrB8() {
        return rB8;
    }

    public void setrB8(Integer rB8) {
        this.rB8 = rB8;
    }

    public Integer getrB7() {
        return rB7;
    }

    public void setrB7(Integer rB7) {
        this.rB7 = rB7;
    }

    public Integer getrB6() {
        return rB6;
    }

    public void setrB6(Integer rB6) {
        this.rB6 = rB6;
    }

    public Integer getrB5() {
        return rB5;
    }

    public void setrB5(Integer rB5) {
        this.rB5 = rB5;
    }

    public Integer getrB4() {
        return rB4;
    }   

    public void setrB4(Integer rB4) {
        this.rB4 = rB4;
    }

    public Integer getrB3() {
        return rB3;
    }

    public void setrB3(Integer rB3) {
        this.rB3 = rB3;
    }

    public Integer getrB2() {
        return rB2;
    }

    public void setrB2(Integer rB2) {
        this.rB2 = rB2;
    }
}
