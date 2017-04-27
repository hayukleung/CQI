package com.gdgs.cqi.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.gdgs.cqi.common.wrapper.XLog;
import java.util.ArrayList;
import java.util.List;

import static com.gdgs.cqi.database.ProductDao.TABLENAME;

/**
 * 自定义查询 ProductDao
 */
public class ProductDaoExtend {

  public static List<Integer> queryCategory(SQLiteDatabase database) {
    Cursor cursor = database.rawQuery("select CATEGORY from " + TABLENAME + " group by CATEGORY", null);
    List<Integer> result = new ArrayList<>(null != cursor ? cursor.getCount() : 0);
    try {
      if (cursor != null && cursor.moveToFirst()) {
        do {
          result.add(cursor.getInt(0));
        } while (cursor.moveToNext());
      }
    } catch (Exception e) {
      XLog.e(e);
    } finally {
      if (cursor != null) cursor.close();
    }
    return result;
  }
}
