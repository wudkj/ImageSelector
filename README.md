# ImageSelector
微信图片选择器

##效果
![ImageSelector](pic/ImageSelector.gif)

##使用方法

		SumileImageChooserUtil.startThisForPics(MainActivity.this, 6, new SumileImageChooserUtil.ActivityResult() {
		    @Override
		    public void onActivityResult(Intent data) {
		        ArrayList<String> list = (ArrayList<String>) data.getSerializableExtra("data");
		        Log.e("sumile", String.valueOf(list.size()));
		        for (String str : list) {
		            TextView tv=new TextView(MainActivity.this);
		            tv.setText(str);
		            BitmapCache cache = new BitmapCache();
		            activityMain.addView(tv);
		        }
		    }
		});

-	参数
	-	第一个参数为上下文
	-	第二个参数为最多选择的个数
	-	第三个是选择完后的回调

-	注意
	-	如果没有选择，回调方法不会被调用
	-	如果Intent的data是空，回调方法就不会被进入，所以没有必要判断是否为null了

该module(A)需要引用另一个module(B),(B)地址为：https://github.com/wudkj/ImageViewpager ，(B)也可单独使用