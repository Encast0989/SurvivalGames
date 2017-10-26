package club.encast.survivalgames.mode;

public enum ModifiedModeType {

    EXPLOSIVE(new ExplosiveMode()),
    KNOCKBACK(new KnockbackMode()),
    ANTI_KNOCKBACK(new AntiKnockbackMode());

    private ModifiedMode modifiedMode;

    ModifiedModeType(ModifiedMode modifiedMode) {
        this.modifiedMode = modifiedMode;
    }

    public ModifiedMode getModifiedMode() {
        return modifiedMode;
    }
}
