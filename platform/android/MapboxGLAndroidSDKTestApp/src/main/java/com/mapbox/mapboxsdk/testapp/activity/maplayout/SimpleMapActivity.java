package com.mapbox.mapboxsdk.testapp.activity.maplayout;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.mapbox.mapboxsdk.annotations.circle.Circle;
import com.mapbox.mapboxsdk.annotations.circle.CircleManager;
import com.mapbox.mapboxsdk.annotations.fill.Fill;
import com.mapbox.mapboxsdk.annotations.fill.FillManager;
import com.mapbox.mapboxsdk.annotations.line.Line;
import com.mapbox.mapboxsdk.annotations.line.LineManager;
import com.mapbox.mapboxsdk.annotations.symbol.Symbol;
import com.mapbox.mapboxsdk.annotations.symbol.SymbolManager;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.testapp.R;
import timber.log.Timber;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.mapbox.mapboxsdk.style.layers.Property.LINE_CAP_ROUND;

/**
 * Test activity showcasing a simple MapView without any MapboxMap interaction.
 */
public class SimpleMapActivity extends AppCompatActivity {

  private final Random random = new Random();
  private static final String MAKI_ICON_AIRPORT = "airport-15";
  private static final String MAKI_ICON_CAR = "car-15";
  private static final String MAKI_ICON_CAFE = "cafe-15";

  private MapView mapView;
  private Symbol symbol;
  private Circle circle;
  private Line line;
  private Fill fill;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_map_simple);
    mapView = findViewById(R.id.mapView);
    mapView.onCreate(savedInstanceState);
    mapView.getMapAsync(mapboxMap -> {
      mapboxMap.moveCamera(CameraUpdateFactory.zoomTo(2));
      createFills(mapboxMap);
      createLines(mapboxMap);
      createCircles(mapboxMap);
      createSymbols(mapboxMap);
    });
  }

  private void createSymbols(MapboxMap mapboxMap) {
    // create symbol manager
    SymbolManager symbolManager = new SymbolManager(mapboxMap);
    symbolManager.setOnSymbolClickListener(symbol -> Timber.e("Symbol clicked with id: %s", symbol.getId()));

    // set non data driven properties
    symbolManager.setIconAllowOverlap(true);
    symbolManager.setTextAllowOverlap(true);

    // create a symbol
    symbol = symbolManager.createSymbol(new LatLng(6.687337, 0.381457));
    symbol.setIconImage(MAKI_ICON_AIRPORT);
    symbol.setIconSize(1.2f);

    // random add symbols across the globe
    Symbol currentSymbol;
    for (int i = 0; i < 500; i++) {
      currentSymbol = symbolManager.createSymbol(createRandomLatLng());
      currentSymbol.setIconImage(MAKI_ICON_CAR);
    }
  }

  private void createCircles(MapboxMap mapboxMap) {
    // create circle manager
    CircleManager circleManager = new CircleManager(mapboxMap);
    circleManager.setOnCircleClickListener(circle -> Timber.e("Symbol clicked with id: %s", circle.getId()));

    // set non data driven properties
    // circleManager.

    // create a circle
    circle = circleManager.createCircle(new LatLng(-4.276448, 14.516245));
    circle.setCircleColor(PropertyFactory.colorToRgbaString(Color.RED));
    circle.setCircleRadius(20f);

  }

  private void createLines(MapboxMap mapboxMap) {
    // create line manager
    LineManager lineManager = new LineManager(mapboxMap);
    lineManager.setOnLineClickListener(line -> Timber.e("Symbol clicked with id: %s", line.getId()));

    // set non data driven properties
    lineManager.setLineCap(LINE_CAP_ROUND);

    // create line
    List<LatLng> latLngs = new ArrayList<>();
    latLngs.add(new LatLng(-2.178992, -4.375974));
    latLngs.add(new LatLng(-4.107888, -7.639772));
    latLngs.add(new LatLng(2.798737, -11.439207));

    line = lineManager.createLine(latLngs);
    line.setLineColor(PropertyFactory.colorToRgbaString(Color.GREEN));
    line.setLineWidth(3f);
  }

  private void createFills(MapboxMap mapboxMap) {
    // create fill manager
    FillManager fillManager = new FillManager(mapboxMap);
    fillManager.setOnFillClickListener(fill -> Timber.e("Symbol clicked with id: %s", fill.getId()));

    // set non data driven properties
    //fillManager.

    // create fill
    List<LatLng> innerLatLngs = new ArrayList<>();
    innerLatLngs.add(new LatLng(-10.733102, -3.363937));
    innerLatLngs.add(new LatLng(-19.716317, 1.754703));
    innerLatLngs.add(new LatLng(-21.085074, -15.747196));

    List<List<LatLng>> latLngs = new ArrayList<>();
    latLngs.add(innerLatLngs);

    fill = fillManager.createFill(latLngs);
    fill.setFillColor(PropertyFactory.colorToRgbaString(Color.YELLOW));
    fill.setFillOutlineColor(PropertyFactory.colorToRgbaString(Color.RED));
  }

  private LatLng createRandomLatLng() {
    return new LatLng((random.nextDouble() * -180.0) + 90.0,
      (random.nextDouble() * -360.0) + 180.0);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_symbolmanager, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.menu_action_icon) {
      symbol.setIconImage(MAKI_ICON_CAFE);
    } else if (item.getItemId() == R.id.menu_action_rotation) {
      symbol.setIconRotate(45.0f);
    } else if (item.getItemId() == R.id.menu_action_text) {
      symbol.setTextField("Hello world!");
    } else if (item.getItemId() == R.id.menu_action_anchor) {
      symbol.setIconAnchor(Property.ICON_ANCHOR_BOTTOM);
    } else if (item.getItemId() == R.id.menu_action_opacity) {
      symbol.setIconOpacity(0.5f);
    } else if (item.getItemId() == R.id.menu_action_offset) {
      symbol.setIconOffset(new Float[] {10.0f, 20.0f});
    } else if (item.getItemId() == R.id.menu_action_text_anchor) {
      symbol.setTextAnchor(Property.TEXT_ANCHOR_TOP);
    } else if (item.getItemId() == R.id.menu_action_text_color) {
      symbol.setTextColor(PropertyFactory.colorToRgbaString(Color.WHITE));
    } else if (item.getItemId() == R.id.menu_action_text_size) {
      symbol.setTextSize(22f);
    } else {
      return super.onOptionsItemSelected(item);
    }
    return true;
  }

  @Override
  protected void onStart() {
    super.onStart();
    mapView.onStart();
  }

  @Override
  protected void onResume() {
    super.onResume();
    mapView.onResume();
  }

  @Override
  protected void onPause() {
    super.onPause();
    mapView.onPause();
  }

  @Override
  protected void onStop() {
    super.onStop();
    mapView.onStop();
  }

  @Override
  public void onLowMemory() {
    super.onLowMemory();
    mapView.onLowMemory();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mapView.onDestroy();
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    mapView.onSaveInstanceState(outState);
  }
}
