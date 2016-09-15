package physics.puzzle.managers;

import android.content.Context;
import android.content.SharedPreferences;

public class Storage 
{
   public static final String _Stroge_Name = "Game_Storage";
   private static SharedPreferences _Settings = null;
   private static SharedPreferences.Editor _Editor = null;
   private static Context _Context = null;
   
   public static void Initializiation(Context context)
   {
	   _Context = context;
   }
   public static void Initializiation()
   {
	   _Settings = _Context.getSharedPreferences(_Stroge_Name, Context.MODE_PRIVATE);
	   _Editor = _Settings.edit();
   }
   
   public static void Add(String _Key, int _Values)
   {
	   if(_Settings == null)
	   {
		   Initializiation();
	   }
	   _Editor.putInt(_Key, _Values);
	   _Editor.commit();
   }
   public static int Get(String _Key)
   {
	   if(_Settings == null)
	   {
		   Initializiation();
	   }
	return _Settings.getInt(_Key, 1);
   }
}
