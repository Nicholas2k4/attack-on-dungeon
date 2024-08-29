package com.mygdx.game.Interfaces;

import com.mygdx.game.Weapon.AttackParticle;

public interface CanAttack {
    AttackParticle attack();
    float getAttackAnimationTime();
}
