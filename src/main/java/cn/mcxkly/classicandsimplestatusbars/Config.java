package cn.mcxkly.classicandsimplestatusbars;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import java.awt.*;

@Mod.EventBusSubscriber(modid = ClassicAndSimpleStatusBars.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.BooleanValue AllOn = BUILDER
            .comment("我不是很推荐关闭功能，因为某种情况下无法解决冲突，比如本模组的图片高度是5像素点，但原版图标是9像素点，如果你有兼容性的问题建议联系我。" +
                    "\nI don't really recommend turning off the function, because there is no way to resolve the conflict in some cases, for example, the image height of this module is 5 pixels, but the original icon is 9 pixels, if you have compatibility problems, please contact me." +
                    "\nIf you can help me, feel free to make a merge request." +
                    "\n你可以在网络上查找你需要的颜色代码，其中之一: https://www.ysdaima.com/hexbiao" +
                    "\nYou can check the color code you need on the web. (HEXADECIMAL)" +
                    "\n" +
                    "\n启用本模组。" +
                    "\nTurn All Features On Or Off | what-a-painful-realization." +
                    "\n默认值(Default)：true")
            .define("All-functional-status", true);
    private static final ForgeConfigSpec.ConfigValue<String> Interval_Textis = BUILDER
            .comment("\n" + "定义'当前值和最大值'间隔所使用的符号。" +
                    "\nDefines the symbol used for the 'Current' and 'Maximum' intervals." +
                    "\n默认值(Default)：Interval_String_1=\"/\"")
            .define("Interval_String_1", "/");
    private static final ForgeConfigSpec.ConfigValue<String> Interval__Text1 = BUILDER
            .comment("\n" + "定义'当前值'附加内容所使用的符号，如：+16伤害吸收。" +
                    "\n\"Defines the symbol used for 'Current Value Add-on'." +
                    "\n默认值(Default)：Interval_String_2=\"+\"")
            .define("Interval_String_2", "+");

    private static final ForgeConfigSpec.ConfigValue<String> Prefix_Health = BUILDER
            .comment("\n" + "定义血量的前缀，如：‘血量:’20+5/40" +
                    "\n\"A prefix that defines the amount of health, e.g. 'HP:'20+5/40" +
                    "\n默认值(Default)：Prefix_String=\"\"")
            .define("Prefix_String", "");

    private static final ForgeConfigSpec.ConfigValue<String> Prefix_Food = BUILDER
            .comment("\n" + "定义血量的前缀，如：‘血量:’20+5/40" +
                    "\n\"A prefix that defines the amount of health, e.g. 'HP:'20+5/40" +
                    "\n默认值(Default)：Prefix_String=\"\"")
            .define("Prefix_String", "");

    private static final ForgeConfigSpec.BooleanValue Bloodsucker_On1 = BUILDER
            .comment("\n" + "如果设置false,当玩家被感染成吸血鬼时，关闭本模组的饱食度显示。如果为ture，将代替吸血鬼模组的血条进行显示。" +
                    "\nIf set to false, when the player is infected as a vampire, the saturation display of this mod will be turned off. If it is TURE, it will be displayed in place of the vampire mod's health bar." +
                    "\n默认值(Default)：true")
            .define("Bloodsucker-functional-status", true);

    private static final ForgeConfigSpec.BooleanValue Food_On1 = BUILDER
            .comment("\n" + "如果设置false,将关闭以经验等级右侧的所有功能修改。" +
                    "\nIf set to false, all feature modifications to the right of the experience level will be turned off." +
                    "\n默认值(Default)：true")
            .define("Food-functional-status", true);

    private static final ForgeConfigSpec.BooleanValue Health_On1 = BUILDER
            .comment("\n" + "如果设置false,将关闭以经验等级左侧的所有功能修改。" +
                    "\nIf set to false, all feature modifications to the left of the experience level will be turned off." +
                    "\n默认值(Default)：true")
            .define("Health-functional-status", true);

    private static final ForgeConfigSpec.BooleanValue EasyMode = BUILDER
            .comment("\n" + "如果设置true,在你关闭全部功能或单独关闭左测/右测功能时生效，会在健康值左侧/饱食度右侧显示文本.如果设置为false则什么都不做。" +
                    "\nIf set to true, it will take effect when you turn off all functions or turn off the left/right test function separately, and the text will be displayed to the left of the health value/to the right of satiety. If set to false, nothing is done." +
                    "\n默认值(Default)：true")
            .define("EasyMode-status", true);


    private static final ForgeConfigSpec.BooleanValue Armour_On1 = BUILDER
            .comment("\n" + "如果设置为false,将关闭盔甲值的显示。 " +
                    "\nIf set to false, the display of armor values is turned off" +
                    "\n默认值(Default)：true")
            .define("Armour-functional-status", true);

    private static final ForgeConfigSpec.BooleanValue Armor_Toughness_On1 = BUILDER
            .comment("\n" + "如果设置为false,将关闭盔甲韧性的显示。" +
                    "\nIf set to false, the display of armor toughness values is turned off" +
                    "\n默认值(Default)：true")
            .define("Armor_Toughness-functional-status", true);

    private static final ForgeConfigSpec.BooleanValue Air_On1 = BUILDER
            .comment("\n" + "如果设置为false,将关闭氧气值的显示。" +
                    "\nIf Set To false The Displayed Of Oxygen Value Is Turned Off | I can't breathe, but I seem to be alive." +
                    "\n默认值(Default)：true")
            .define("Air-functional-status", true);

    private static final ForgeConfigSpec.BooleanValue Mounts_On1 = BUILDER
            .comment("\n" + "如果设置为false,将关闭坐骑健康值的显示。" +
                    "\nIf set to false, the display of mount health values will be turned off." +
                    "\n默认值(Default)：true")
            .define("Mounts-functional-status", true);

    private static final ForgeConfigSpec.ConfigValue<String> Color_Health1 = BUILDER
            .comment("\n" + "血量值文本颜色。" +
                    "\nHealth value text color." +
                    "\n默认值(Default)：\"#EE0000\"")
            .define("Color_Health", "#EE0000");

    private static final ForgeConfigSpec.ConfigValue<String> Color_Health_Absorb1 = BUILDER
            .comment("\n" + "伤害吸收文本颜色。" +
                    "\nHealth Absorb value text color." +
                    "\n默认值(Default)：\"#EE0000\"")
            .define("Color_Health_Absorb", "#EE0000");

    private static final ForgeConfigSpec.ConfigValue<String> Color_Health_Tail1 = BUILDER
            .comment("\n" + "最大血量文本颜色。" +
                    "\nMax Health value text color." +
                    "\n默认值(Default)：\"#EE0000\"")
            .define("Color_Health_Tail", "#EE0000");

    private static final ForgeConfigSpec.ConfigValue<String> Color_Food1 = BUILDER
            .comment("\n" + "饱食度文本颜色。" +
                    "\nFood value text color." +
                    "\n默认值(Default)：\"#EE0000\"")
            .define("Color_Food", "#EE0000");

    private static final ForgeConfigSpec.ConfigValue<String> Color_Food_Saturation1 = BUILDER
            .comment("\n" + "饱和度文本颜色。" +
                    "\nSaturation value text color." +
                    "\n默认值(Default)：\"#EE0000\"")
            .define("Color_Food_Saturation", "#EE0000");

    private static final ForgeConfigSpec.ConfigValue<String> Color_Food_Tail1 = BUILDER
            .comment("\n" + "最大饱食度文本颜色。" +
                    "\nMax Food value text color." +
                    "\n默认值(Default)：\"#EE0000\"")
            .define("Color_Food_Tail", "#EE0000");
    private static final ForgeConfigSpec.ConfigValue<String> Color_Armor1 = BUILDER
            .comment("\n" + "盔甲文本颜色。" +
                    "\nArmor value text color." +
                    "\n默认值(Default)：\"#EE0000\"")
            .define("Color_Armor", "#EE0000");
    private static final ForgeConfigSpec.ConfigValue<String> Color_Armor_Toughness1 = BUILDER
            .comment("\n" + "盔甲韧性文本颜色。" +
                    "\nArmor Toughness value text color." +
                    "\n默认值(Default)：\"#EE0000\"")
            .define("Color_Armor_Toughness", "#EE0000");

    private static final ForgeConfigSpec.ConfigValue<String> Interval_String_1_ = BUILDER
            .comment("\n" + "符号'/'的颜色。" +
                    "\nThe color of the symbol '/'." +
                    "\n默认值(Default)：\"#E0EEEE\"")
            .define("Color_Interval_String_1", "#E0EEEE");
    private static final ForgeConfigSpec.ConfigValue<String> Interval_String_2_ = BUILDER
            .comment("\n" + "符号'+'的颜色。" +
                    "\nThe color of the symbol '+'." +
                    "\n默认值(Default)：\"#BFEFFF\"")
            .define("Color_Interval_String_2", "#BFEFFF");
    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean All_On;
    public static String Interval_Text;
    public static boolean Bloodsucker_On;
    public static boolean Food_On;
    public static boolean Health_On;
    public static boolean EasyMode_Text_On;
    public static boolean Armour_On;
    public static boolean Armor_Toughness_On;
    public static boolean Air_On;
    public static boolean Mounts_On;
    public static int Color_Health;
    public static int Color_Health_Absorb;
    public static int Color_Health_Tail;
    public static int Color_Food;
    public static int Color_Food_Saturation;
    public static int Color_Food_Tail;
    public static int Color_Armor;
    public static int Color_Armor_Toughness;
    public static int Interval_String_1;
    public static int Interval_String_2;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        try {
            All_On = AllOn.get();
            Interval_Text = Interval_Textis.get();
            Bloodsucker_On = Bloodsucker_On1.get();
            Food_On = Food_On1.get();
            Health_On = Health_On1.get();
            EasyMode_Text_On = EasyMode.get();
            Armour_On = Armour_On1.get();
            Armor_Toughness_On = Armor_Toughness_On1.get();
            Air_On = Air_On1.get();
            Mounts_On = Mounts_On1.get();

            Color_Health = Integer.parseInt(Color_Health1.get().substring(1), 16);
            Color_Health_Absorb = Integer.parseInt(Color_Health_Absorb1.get().substring(1), 16);
            Color_Health_Tail = Integer.parseInt(Color_Health_Tail1.get().substring(1), 16);
            Color_Food = Integer.parseInt(Color_Food1.get().substring(1), 16);
            Color_Food_Saturation = Integer.parseInt(Color_Food_Saturation1.get().substring(1), 16);
            Color_Food_Tail = Integer.parseInt(Color_Food_Tail1.get().substring(1), 16);
            Color_Armor = Integer.parseInt(Color_Armor1.get().substring(1), 16);
            Color_Armor_Toughness = Integer.parseInt(Color_Armor_Toughness1.get().substring(1), 16);
            Interval_String_1 = Integer.parseInt(Interval_String_1_.get().substring(1), 16);
            Interval_String_2 = Integer.parseInt(Interval_String_2_.get().substring(1), 16);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }
}
