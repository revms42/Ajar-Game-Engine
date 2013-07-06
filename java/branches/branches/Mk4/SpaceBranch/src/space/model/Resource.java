package space.model;

import java.awt.Color;

public enum Resource {

	//Goldschmidt classification
	
	PRODUCTION("Production","Pro",false,Color.WHITE),
	/* Gas loving elements: */
	MINERAL1("Atmophile Elements","Atm",true,Color.CYAN,
			Element.Ar,
			Element.C,
			Element.Kr,
			Element.H,
			Element.He,
			Element.N,
			Element.Ne,
			Element.Rn,
			Element.Xe),
	/* Silicon loving elements: */
	MINERAL2("Lithophile Elements","Lth",true,Color.YELLOW,
			Element.Al, 
			Element.At, 
			Element.B, 
			Element.Ba, 
			Element.Be, 
			Element.Br, 
			Element.Ca,
			Element.Ce,
			Element.Cl, 
			Element.Cr, 
			Element.Cs,
			Element.Dy,
			Element.Er,
			Element.Eu,
			Element.F,
			Element.Gd,
			Element.I, 
			Element.Hf,
			Element.Ho,
			Element.K,
			Element.La,
			Element.Li,
			Element.Lu,
			Element.Mg, 
			Element.Na, 
			Element.Nb,
			Element.Nd,
			Element.O, 
			Element.P,
			Element.Pm,
			Element.Pr,
			Element.Rb, 
			Element.Sc, 
			Element.Si,
			Element.Sm,
			Element.Sr, 
			Element.Ta,
			Element.Tb,
			Element.Th, 
			Element.Ti,
			Element.Tm,
			Element.U, 
			Element.V,
			Element.W,
			Element.Y,
			Element.Yb,
			Element.Zr),
	/* Iron loving elements: */
	MINERAL3("Siderophile Elements","Sid",true,Color.RED,
			Element.Au,
			Element.Co,
			Element.Fe,
			Element.Ir,
			Element.Mn,
			Element.Mo,
			Element.Ni,
			Element.Os,
			Element.Pd,
			Element.Pt,
			Element.Re,
			Element.Rh,
			Element.Ru),
	/* Copper loving elements: */
	MINERAL4("Chalcophile Elements","Cha",true,Color.GREEN,
			Element.Ag, 
			Element.As, 
			Element.Bi, 
			Element.Cd, 
			Element.Cu, 
			Element.Ga, 
			Element.Ge, 
			Element.Hg, 
			Element.In, 
			Element.Pb, 
			Element.Po, 
			Element.S, 
			Element.Sb, 
			Element.Se, 
			Element.Sn, 
			Element.Te, 
			Element.Tl, 
			Element.Zn),
	MINERAL5("Xenophile Elements","Xen",false,Color.MAGENTA,
			Element.Fr,
			Element.Ra,
			Element.Ac,
			Element.Pa,
			Element.Np,
			Element.Pu,
			Element.Am,
			Element.Cm,
			Element.Bk,
			Element.Cf,
			Element.Es,
			Element.Fm,
			Element.Md,
			Element.No,
			Element.Lr,
			Element.Rf,
			Element.Db,
			Element.Sg,
			Element.Bh,
			Element.Hs,
			Element.Mt,
			Element.Ds,
			Element.Rg,
			Element.Lz,
			Element.Fv,
			Element.Kn,
			Element.Nx,
			Element.Qx,
			Element.Sx,
			Element.Px,
			Element.Xx,
			Element.Zx);
	
	private final String name;
	private final String shortName;
	private final boolean natural;
	private final Color color;
	private final Element[] elements;
	
	private float ppm;
	private float pWeight;
	
	public final static Resource[] RESOURCES = {PRODUCTION,MINERAL1,MINERAL2,MINERAL3,MINERAL4,MINERAL5};
	
	private Resource(String name, String shortName, boolean natural, Color color, Element... elements){
		this.name = name;
		this.elements = elements;
		this.shortName = shortName;
		this.natural = natural;
		this.color = color;
		
		ppm = 0f;
		pWeight = 0f;
	}
	
	public String toString(){
		return name;
	}
	
	public float ppmSi(){
		if(ppm == 0f && elements != null){
			for(Element i : elements){
				ppm += i.abundance;
			}
		}
		
		return ppm;
	}
	
	public float partialWeight(){
		if(pWeight == 0f && elements != null){
			for(Element i : elements){
				pWeight += (i.abundance*i.atomicWeight);
			}
		}
		
		return pWeight;
	}
	
	public String shortName(){
		return shortName;
	}
	
	public boolean isNatural(){
		return natural;
	}
	
	public Color uiColor(){
		return color;
	}
	
	private enum Element {
		//Cosmochemical Periodic Table of the Elements in the Solar System
		
		H 	(24300000000f, 1.00794f),
		He 	(2343000000f, 4.002602f),
		Li 	(55.47f, 6.941f),
		Be 	(0.7374f, 9.012182f),
		B 	(17.32f, 10.811f),
		C 	(7079000f, 12.0107f),
		N 	(1950000f, 	14.007f),
		O 	(14130000f, 15.9994f),
		F 	(841.1f, 18.9984032f),
		Ne 	(2148000f, 20.1797f),
		Na 	(57510f, 22.98976928f),
		Mg 	(1020000f, 24.3050f),
		Al 	(84100f, 26.9815386f),
		Si 	(1000000f, 28.0855f),
		P 	(8373f, 30.973762f),
		S 	(444900f, 32.065f),
		Cl 	(5237f, 35.453f),
		Ar 	(102500f, 39.948f),
		K 	(3692f, 39.0983f),
		Ca 	(62870f, 40.078f),
		Sc 	(34.20f, 44.955912f),
		Ti 	(2422f, 47.867f),
		V 	(288.4f, 50.9415f),
		Cr 	(12860f, 51.9961f),
		Mn 	(9168f, 54.938045f),
		Fe 	(838000f, 55.845f),
		Co 	(2323f, 58.933195f),
		Ni 	(47800f, 58.6934f),
		Cu 	(527f, 63.546f),
		Zn 	(1226f, 65.38f),
		Ga 	(35.97f, 69.723f),
		Ge 	(120.6f, 72.64f),
		As 	(6.089f, 74.92160f),
		Se 	(65.79f, 78.96f),
		Br 	(11.32f, 79.904f),
		Kr 	(55.15f, 83.798f),
		Rb 	(6.572f, 85.4678f),
		Sr 	(23.64f, 87.62f),
		Y 	(4.608f, 88.90585f),
		Zr 	(11.33f, 91.224f),
		Nb 	(0.7554f, 92.90638f),
		Mo 	(2.601f, 95.94f),
		Tc	(0.0f, 98.0f), //No stable isotope
		Ru 	(1.900f, 101.07f),
		Rh 	(0.3708f, 102.90550f),
		Pd 	(1.435f, 106.42f),
		Ag 	(0.4913f, 107.8682f),
		Cd 	(1.584f, 112.411f),
		In 	(0.1810f, 114.818f),
		Sn 	(3.733f, 118.710f),
		Sb 	(0.3292f, 121.760f),
		Te 	(4.815f, 127.60f),
		I 	(0.9975f, 126.90447f),
		Xe 	(5.391f, 131.293f),
		Cs 	(0.3671f, 132.9054519f),
		Ba 	(4.351f, 137.327f),
		La 	(0.4405f, 138.90547f),
		Ce 	(1.169f, 140.116f),
		Pr 	(0.1737f, 140.90765f),
		Nd 	(0.8355f, 144.242f),
		Pm	(0.0f, 145.0f), //No stable isotope
		Sm 	(0.2542f, 150.36f),
		Eu 	(0.09513f, 151.964f),
		Gd 	(0.3321f, 157.25f),
		Tb 	(0.05907f, 158.92535f),
		Dy 	(0.3862f, 162.500f),
		Ho 	(0.08986f, 164.93032f),
		Er 	(0.2554f, 167.259f),
		Tm 	(0.0370f, 168.93421f),
		Yb 	(0.2484f, 173.04f),
		Lu 	(0.03572f, 174.967f),
		Hf 	(0.1699f, 178.49f),
		Ta 	(0.02099f, 180.94788f),
		W 	(0.1277f, 183.84f),
		Re 	(0.05254f, 186.207f),
		Os 	(0.6738f, 190.23f),
		Ir 	(0.6448f, 192.217f),
		Pt 	(1.357f, 195.084f),
		Au 	(0.1955f, 196.966569f),
		Hg 	(0.4128f, 200.59f),
		Tl 	(0.1845f, 204.3833f),
		Pb 	(3.258f, 207.2f),
		Bi 	(0.1388f, 208.98040f),
		Po	(0.0f, 209.0f), //No stable isotope
		At	(0.0f, 210.0f), //No stable isotope
		Rn	(0.0f, 222.0f), //No stable isotope
		Fr	(0.0f, 223.0f), //No stable isotope
		Ra	(0.0f, 226.0f), //No stable isotope
		Ac	(0.0f, 227.0f), //No stable isotope
		Th 	(0.03512f, 232.0381f),
		Pa	(0.0f, 231.03588f), //No stable isotope
		U 	(.00931f, 238.02891f),
		Np	(0.0f, 237.0f), //No stable isotope
		Pu	(0.0f, 244.0f), //No stable isotope
		Am	(0.0f, 243.0f), //No stable isotope
		Cm	(0.0f, 247.0f), //No stable isotope
		Bk	(0.0f, 247.0f), //No stable isotope
		Cf	(0.0f, 251.0f), //No stable isotope
		Es	(0.0f, 252.0f), //No stable isotope
		Fm	(0.0f, 257.0f), //No stable isotope
		Md	(0.0f, 258.0f), //No stable isotope
		No	(0.0f, 259.0f), //No stable isotope
		Lr	(0.0f, 262.0f), //No stable isotope
		Rf	(0.0f, 267.0f), //No stable isotope
		Db	(0.0f, 268.0f), //No stable isotope
		Sg	(0.0f, 271.0f), //No stable isotope
		Bh	(0.0f, 270.0f), //No stable isotope
		Hs	(0.0f, 277.0f), //No stable isotope
		Mt	(0.0f, 276.0f), //No stable isotope
		Ds	(0.0f, 281.0f), //No stable isotope
		Rg	(0.0f, 280.0f), //No stable isotope
		Lz	(0.0f, 289.0f), //No stable isotope
		Fv	(0.0f, 304.0f), //Island of stability candidate Ubn 120
		Kn	(0.0f, 310.0f), //Island of stability candidate Ubh 126, aka "Kryptonite"
		Nx	(0.0f, 1000.0f), //Neutronium
		Qx	(0.0f, 3000.0f), //Quarkium
		Sx	(0.0f, 60000.0f), //Strangium
		Px	(0.0f, 1000000.0f), //Preonium
		Xx	(0.0f, Float.MAX_VALUE), //Singulanium
		Zx	(0.0f, Float.POSITIVE_INFINITY); //Unobtainium
			
		private final float abundance;
		private final float atomicWeight;
		
		private Element(float abundance, float atomicWeight){
			this.abundance = abundance;
			this.atomicWeight = atomicWeight;
		}
	}
}
