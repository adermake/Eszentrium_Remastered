package esze.utils;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.server.v1_15_R1.PacketDataSerializer;
import net.minecraft.server.v1_15_R1.Particle;
import net.minecraft.server.v1_15_R1.ParticleParam;






public class ParticleParamNormal implements ParticleParam {

	private final Particle b;
	
	@Override
	public String a() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void a(PacketDataSerializer arg0) {
		// TODO Auto-generated method stub
		
	}

	
	
	
	@SuppressWarnings("unchecked")
	public static final ParticleParam.a<ParticleParamNormal> a = new ParticleParam.a()
	  {

		@Override
		public ParticleParam b(Particle arg0, StringReader arg1) throws CommandSyntaxException {
			
		     return null;
			
		}

		@Override
		public ParticleParam b(Particle arg0, PacketDataSerializer arg1) {
			// TODO Auto-generated method stub
			return null;
		}
	   
	  };
	  
	
	  public ParticleParamNormal(Particle paramParticle)
	  {
	    this.b = paramParticle;
	  }

	@Override
	public Particle<?> getParticle() {
		// TODO Auto-generated method stub
		return b;
	}

	

	
	
}
