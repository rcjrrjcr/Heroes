package com.herocraftonline.dev.heroes.classes;

public class SkillSettings {
	public final int LevelRequirement;
	public final int ManaCost;
	public final int Cooldown;
	
	public SkillSettings(int levelRequirement, int manaCost, int cooldown) {
		this.LevelRequirement = levelRequirement;
		this.ManaCost = manaCost;
		this.Cooldown = cooldown;
	}
}
