package io.github.racoondog.legacyhypixelfixes.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.Team;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(Scoreboard.class)
public abstract class ScoreboardMixin {
    @Inject(method = "removeTeam", at = @At("HEAD"), cancellable = true)
    private void preventLogSpam$0(Team team, CallbackInfo ci) {
        if (team == null) ci.cancel();
    }

    @Inject(method = "removeObjective", at = @At("HEAD"), cancellable = true)
    private void preventLogSpam$1(ScoreboardObjective objective, CallbackInfo ci) {
        if (objective == null) ci.cancel();
    }
}
