/**

Copyright (c) 2007 Matthew E. Foster

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
**/

var FishEyeToolBar = Class.create();

Object.extend(Object.extend(FishEyeToolBar.prototype, EventDispatcher.prototype),
				{
					
					initialize : function(ele, options){
						
						this.elementArr = [];
												
						this.options =  {selector : "img",
										 createSub : this.createSub.bind(this),
										 subOptions : {}};
						
						Object.extend(this.options, options);
						
						this.createListener();
						this.buildInterface(ele);
						this.attachListener();
						this.resetTimer = false;				
					
					},
					createSub : function(ele){
						
						return new FishEyeItem(ele, this.options.subOptions);
					
					},
					buildInterface : function(ele){
						this.container = $(ele);
						 
						this.container.getElementsBySelector(this.options.selector).collect(this.buildItem.bind(this));
					
					},
					buildItem : function(ele){
						
						var obj = this.options.createSub(ele, this.options.subOptions);
						
						obj.addEventListener("click", this.itemClickHandle);
						
						this.elementArr.push(obj);
						
						this.dispatchEvent("itemBuild", obj);												
					
					},
					createListener : function(e){
						
						this.mouseMoveHandle = this.handleMouseMove.bindAsEventListener(this);
						this.itemClickHandle = this.handleItemClick.bind(this);
						this.mouseOutHandle = this.handleMouseOut.bindAsEventListener(this);
						this.cancelTimerHandle = this.cancelTimer.bindAsEventListener(this);
					},
					attachListener : function(){
						
						Event.observe(this.container, "mousemove", this.mouseMoveHandle);
						Event.observe(this.container, "mouseout", this.mouseOutHandle);
						Event.observe(this.container, "mouseover", this.cancelTimerHandle);
											
					},
					cancelTimer : function(){
						
						clearTimeout(this.resetTimer);
						
					},
					handleMouseMove : function(e){
						
						this.cancelTimer();
						
						this.elementArr.invoke("handleFishEye", { x : Event.pointerX(e), y : Event.pointerY(e) });
					
					},
					handleMouseOut : function(e){
					
						this.resetTimer = setTimeout(this.resetElements.bind(this), 1000);						
						
					},
					
					resetElements : function(){
					
						this.elementArr.invoke('resetElement');
					
					},
					handleItemClick : function(e){
						
						this.dispatchEvent("itemClick", e);
					
					},
					elements : function(){
						
						return this.elementArr;
						
					}	
				}
			);
	
	
var FishEyeItem = Class.create();


Object.extend(Object.extend(FishEyeItem.prototype, EventDispatcher.prototype),
				{	
					initialize : function(ele, options){
						
						this.options = {
											scaleFactor : 0.5,
											scaleThrottle : 200
										}
										
						Object.extend(this.options, options || {});
						
						
						this.buildInterface(ele);
						this.createListener();
						this.attachListener();
						
						this.setOriginalProperties();
						
						
						this.lastScale = 1;
						
					},
					setOriginalProperties : function(){
						
						this.originalHeight =parseInt(this.ele.getStyle("height").replace(/[^0-9]/gi, ""));
					
						this.originalWidth =parseInt(this.ele.getStyle("width").replace(/[^0-9]/gi, "")); 
						
						this.originalMarginTop= parseInt(this.ele.getStyle("margin-top").replace(/[^0-9]/gi, ""));
						
					},
					buildInterface : function(ele){
						
						this.ele = $(ele);
																									
					},
					createListener : function(e){
					
						this.fishEyeHandle = this.handleFishEye.bind(this);
						this.clickHandle = this.handleClick.bindAsEventListener(this);
						this.cancelMouseOutHandle = this.handleMouseOut.bindAsEventListener(this);
					
					},
					attachListener : function(){
						
						Event.observe(this.ele, "click", this.clickHandle);
						Event.observe(this.ele, "click", this.cancelMouseOutHandle);	
						
					},
					handleMouseOut : function(e){
						
						Event.stop(e);
						return false;
						
					},
					handleClick : function(e){
						
						this.dispatchEvent("click", e);
					
					},						
					handleFishEye : function(p){
						
						var offset = this.getCenterAxis();
						var distance = Math.abs(p.x - offset);
						
						if(distance > this.options.scaleThrottle){
							this.resetElement();
							return true;
						}
						
						var scale = Math.abs(this.options.scaleThrottle - distance) / this.options.scaleThrottle + 1;
						this.lastScale = scale;
						
						this.scaleElement(scale);
												
					},
					scaleElement : function(scale){
						
						this.ele.setStyle({ zIndex : scale * 100, marginTop : this.originalMarginTop - ((scale * this.originalHeight) - this.originalHeight) + "px",   height : (scale * this.originalHeight) + "px", width : (scale * this.originalWidth) + "px"});													
												
					},
					resetElement : function(){
					
						this.ele.setStyle({ zIndex : 1, marginTop :  this.originalMarginTop + "px",  height : this.originalHeight  + "px", width : this.originalWidth + "px" });
					
					},
					getCenterAxis : function(){
						
						return Math.floor(Position.cumulativeOffset(this.ele).first() + (((this.originalWidth/2)) * this.lastScale));
					
					}		
				}
			);

var FishEyeItemDown = Class.create();

Object.extend(Object.extend(FishEyeItemDown.prototype, FishEyeItem.prototype),
				{
					
					setOriginalProperties : function(){
						
						this.originalHeight =parseInt(this.ele.getStyle("height").replace(/[^0-9]/gi, ""));
					
						this.originalWidth =parseInt(this.ele.getStyle("width").replace(/[^0-9]/gi, "")); 
						
						this.originalMarginBottom = parseInt(this.ele.getStyle("margin-bottom").replace(/[^0-9]/gi, ""));
												
												
					},
					scaleElement : function(scale){
						
						this.ele.setStyle({ zIndex : scale * 100, marginBottom : this.originalMarginBottom - ((scale * this.originalHeight) - this.originalHeight) + "px",   height : (scale * this.originalHeight) + "px", width : (scale * this.originalWidth) + "px"});													
												
					},
					resetElement : function(){
					
						this.ele.setStyle({ zIndex : 1, marginBottom :  this.originalMarginBottom + "px",  height : this.originalHeight  + "px", width : this.originalWidth + "px" });
					
					}
					
					
					
				}
			);
var FishEyeItemVertical = Class.create();

Object.extend(Object.extend(FishEyeItemVertical.prototype, FishEyeItem.prototype),
				{
					handleFishEye : function(p){
						
						var offset = this.getCenterYAxis();
						var distance = Math.abs(p.y - offset);
						
						if(distance > this.options.scaleThrottle){
							this.resetElement();
							return true;
						}
						
						var scale = Math.abs(this.options.scaleThrottle - distance) / this.options.scaleThrottle + 1;
						this.lastScale = scale;
						
						this.scaleElement(scale);
												
					},
					getCenterYAxis : function(){
						
						return Math.floor(Position.cumulativeOffset(this.ele).last() + (((this.originalHeight/2)) * this.lastScale));
					
					},
					setOriginalProperties : function(){
						
						this.originalHeight =parseInt(this.ele.getStyle("height").replace(/[^0-9]/gi, ""));
					
						this.originalWidth =parseInt(this.ele.getStyle("width").replace(/[^0-9]/gi, "")); 
						
					},
					scaleElement : function(scale){
						
						this.ele.setStyle({ zIndex : scale * 100, height : (scale * this.originalHeight) + "px", width : (scale * this.originalWidth) + "px"});													
												
					},
					resetElement : function(){
					
						this.ele.setStyle({ zIndex : 1,  height : this.originalHeight  + "px", width : this.originalWidth + "px" });
					
					}				
				}
			);