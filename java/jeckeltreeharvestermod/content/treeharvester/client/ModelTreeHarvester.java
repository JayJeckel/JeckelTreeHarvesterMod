package jeckeltreeharvestermod.content.treeharvester.client;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTreeHarvester extends ModelBase
{
  //fields
    ModelRenderer top;
    ModelRenderer bottom;
    ModelRenderer box;
    ModelRenderer braceFLT;
    ModelRenderer braceFRT;
    ModelRenderer braceFLB;
    ModelRenderer braceFRB;
    ModelRenderer braceFL;
    ModelRenderer braceFR;
    ModelRenderer braceBL;
    ModelRenderer braceBR;
    ModelRenderer axle;
    ModelRenderer axleL;
    ModelRenderer axleR;
    ModelRenderer blade;
  
  public ModelTreeHarvester()
  {
    textureWidth = 64;
    textureHeight = 64;

    top = new ModelRenderer(this, 0, 0);
    top.addBox(-8F, -16F, -8F, 16, 1, 16);
    top.setRotationPoint(0F, 0F, 0F);
    top.setTextureSize(64, 64);
    top.mirror = true;
    setRotation(top, 0F, 0F, 0F);
    bottom = new ModelRenderer(this, 0, 0);
    bottom.addBox(-8F, -1F, -8F, 16, 1, 16);
    bottom.setRotationPoint(0F, 0F, 0F);
    bottom.setTextureSize(64, 64);
    bottom.mirror = true;
    setRotation(bottom, 0F, 0F, 0F);
    box = new ModelRenderer(this, 0, 17);
    box.addBox(-7F, -12F, -8F, 14, 8, 8);
    box.setRotationPoint(0F, 0F, 0F);
    box.setTextureSize(64, 64);
    box.mirror = true;
    setRotation(box, 0F, 0F, 0F);
    braceFLT = new ModelRenderer(this, 4, 45);
    braceFLT.addBox(6F, -15F, -1F, 1, 3, 1);
    braceFLT.setRotationPoint(0F, 0F, 0F);
    braceFLT.setTextureSize(64, 64);
    braceFLT.mirror = true;
    setRotation(braceFLT, 0F, 0F, 0F);
    braceFRT = new ModelRenderer(this, 4, 45);
    braceFRT.addBox(-7F, -15F, -1F, 1, 3, 1);
    braceFRT.setRotationPoint(0F, 0F, 0F);
    braceFRT.setTextureSize(64, 64);
    braceFRT.mirror = true;
    setRotation(braceFRT, 0F, 0F, 0F);
    braceFLB = new ModelRenderer(this, 4, 45);
    braceFLB.addBox(6F, -4F, -1F, 1, 3, 1);
    braceFLB.setRotationPoint(0F, 0F, 0F);
    braceFLB.setTextureSize(64, 64);
    braceFLB.mirror = true;
    setRotation(braceFLB, 0F, 0F, 0F);
    braceFRB = new ModelRenderer(this, 4, 45);
    braceFRB.addBox(-7F, -4F, -1F, 1, 3, 1);
    braceFRB.setRotationPoint(0F, 0F, 0F);
    braceFRB.setTextureSize(64, 64);
    braceFRB.mirror = true;
    setRotation(braceFRB, 0F, 0F, 0F);
    braceFL = new ModelRenderer(this, 0, 45);
    braceFL.addBox(7F, -15F, -8F, 1, 14, 1);
    braceFL.setRotationPoint(0F, 0F, 0F);
    braceFL.setTextureSize(64, 64);
    braceFL.mirror = true;
    setRotation(braceFL, 0F, 0F, 0F);
    braceFR = new ModelRenderer(this, 0, 45);
    braceFR.addBox(-8F, -15F, -8F, 1, 14, 1);
    braceFR.setRotationPoint(0F, 0F, 0F);
    braceFR.setTextureSize(64, 64);
    braceFR.mirror = true;
    setRotation(braceFR, 0F, 0F, 0F);
    braceBL = new ModelRenderer(this, 0, 45);
    braceBL.addBox(7F, -15F, 7F, 1, 14, 1);
    braceBL.setRotationPoint(0F, 0F, 0F);
    braceBL.setTextureSize(64, 64);
    braceBL.mirror = true;
    setRotation(braceBL, 0F, 0F, 0F);
    braceBR = new ModelRenderer(this, 0, 45);
    braceBR.addBox(-8F, -15F, 7F, 1, 14, 1);
    braceBR.setRotationPoint(0F, 0F, 0F);
    braceBR.setTextureSize(64, 64);
    braceBR.mirror = true;
    setRotation(braceBR, 0F, 0F, 0F);
    axle = new ModelRenderer(this, 0, 33);
    axle.addBox(-7F, -9F, 7F, 14, 1, 1);
    axle.setRotationPoint(0F, 0F, 0F);
    axle.setTextureSize(64, 64);
    axle.mirror = true;
    setRotation(axle, 0F, 0F, 0F);
    axleL = new ModelRenderer(this, 30, 33);
    axleL.addBox(4F, -9F, 0F, 1, 1, 8);
    axleL.setRotationPoint(0F, 0F, 0F);
    axleL.setTextureSize(64, 64);
    axleL.mirror = true;
    setRotation(axleL, 0F, 0F, 0F);
    axleR = new ModelRenderer(this, 30, 33);
    axleR.addBox(-5F, -9F, 0F, 1, 1, 8);
    axleR.setRotationPoint(0F, 0F, 0F);
    axleR.setTextureSize(64, 64);
    axleR.mirror = true;
    setRotation(axleR, 0F, 0F, 0F);
    blade = new ModelRenderer(this, 0, 35);
    blade.addBox(-1F, -11F, 5F, 2, 5, 5);
    blade.setRotationPoint(0F, 0F, 0F);
    blade.setTextureSize(64, 64);
    blade.mirror = true;
    setRotation(blade, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
	    super.render(entity, f, f1, f2, f3, f4, f5);
	    this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);

	    top.render(f5);
	    bottom.render(f5);
	    box.render(f5);
	    braceFLT.render(f5);
	    braceFRT.render(f5);
	    braceFLB.render(f5);
	    braceFRB.render(f5);
	    braceFL.render(f5);
	    braceFR.render(f5);
	    braceBL.render(f5);
	    braceBR.render(f5);
	    axle.render(f5);
	    axleL.render(f5);
	    axleR.render(f5);
	    blade.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  @Override public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
  }
}
