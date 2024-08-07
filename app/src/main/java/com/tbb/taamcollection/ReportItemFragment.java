package com.tbb.taamcollection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.pdf.PdfDocument;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.os.Environment;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class ReportItemFragment extends Fragment {
    public boolean dp = false;
    ItemDatabase db;
    private Spinner spinnerReportType;

    public ReportItemFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.report_item_fragment, container, false);
        db = new ItemDatabase("items");

        // Variables
        Spinner spinnerReportType = view.findViewById(R.id.spinnerReportType);
        Button reportButton = view.findViewById(R.id.reportSubmit);
        Button backButtonReport = view.findViewById(R.id.backButtonReport);
        TextInputEditText reportText = view.findViewById(R.id.reportInput);
        TextView reportStatus = view.findViewById(R.id.reportStatus);


        //Spinner
        ArrayAdapter<CharSequence> itemAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.type_array, R.layout.spinner);
        itemAdapter.setDropDownViewResource(R.layout.spinner);
        spinnerReportType.setAdapter(itemAdapter);

        backButtonReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new HomeFragment());
            }
        });

        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportStatus.setText("Report PDF Document Generated");
                reportStatus.setTextColor(Color.parseColor("#65D304"));

                String type = spinnerReportType.getSelectedItem().toString();

                System.out.println(type);
                if(type.equals("Lot#")){
                    LinkedList<Item> itemsList = db.search("", reportText.getText().toString(), "", "");
                    new CreatePDFTask().execute(itemsList);
                }
               if(type.equals("Name")){
                   LinkedList<Item> itemsList = db.search(reportText.getText().toString(), "", "", "");
                   new CreatePDFTask().execute(itemsList);
               }
               if(type.equals("Category")){
                   LinkedList<Item> itemsList = db.search("", "", reportText.getText().toString(), "");
                   new CreatePDFTask().execute(itemsList);
               }
               if(type.equals("Period")){
                   LinkedList<Item> itemsList = db.search("", "", reportText.getText().toString(), "");
                   new CreatePDFTask().execute(itemsList);
               }
               if(type.equals("Category Desc./Pic.")){
                LinkedList<Item> itemsList = db.search("", "", reportText.getText().toString(), "");
                dp = true;
                new CreatePDFTask().execute(itemsList);
               }
               if(type.equals("Period Desc./Pic.")){
                LinkedList<Item> itemsList = db.search("", "", "", reportText.getText().toString());
                dp = true;
                new CreatePDFTask().execute(itemsList);
               }
                if(type.equals("All")){
                    List<Item> itemsList = new LinkedList<>(db.allItems.values());
                    new CreatePDFTask().execute(itemsList);
                }
               if(type.equals("All Desc./Pic.")){
                   List<Item> itemsList = new LinkedList<>(db.allItems.values());
                   dp = true;
                   new CreatePDFTask().execute(itemsList);
               }


            }
        });

        return view;
    }

    private class CreatePDFTask extends AsyncTask<List<Item>, Void, PdfDocument> {
        @Override
        protected PdfDocument doInBackground(List<Item>... params) {
            List<Item> itemsList = params[0];

            int index = 0;

            PdfDocument document = new PdfDocument();
            int page_num = 1;
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(584, 440, page_num).create();
            PdfDocument.Page page = document.startPage(pageInfo);

            Canvas canvas = page.getCanvas();
            canvas.drawColor(Color.WHITE);

            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setTextSize(20);
            TextPaint textPaint = new TextPaint();
            textPaint.setColor(Color.BLACK);
            textPaint.setTextSize(20);



            for (Item item : itemsList) {
                if(index != 0){
                    System.out.println("hi");
                    document.finishPage(page);
                    pageInfo = new PdfDocument.PageInfo.Builder(584, 440, page_num++).create();
                    page = document.startPage(pageInfo);
                    canvas = page.getCanvas();
                    canvas.drawColor(Color.WHITE);

                }
                Bitmap bitmap = getBitmapFromURL(item.getImageUrl());
                if (bitmap != null) {
                    bitmap = Bitmap.createScaledBitmap(bitmap, 150, 150, false);
                    canvas.drawBitmap(bitmap,50, 50, paint);

                }
                StaticLayout textBox = new StaticLayout(item.getDescription(), textPaint,200, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false );

                canvas.save();
                canvas.translate(300, 50);
                textBox.draw(canvas);
                canvas.restore();

                if(dp == false) {
                    canvas.drawText("Name: " + item.getName(), 50, 250, paint);
                    canvas.drawText("Category: " + item.getCategory().toString(), 50, 280, paint);
                    canvas.drawText("Lot #: " + item.getLotNumber(), 50, 310, paint);
                }

                index++;


            }

            document.finishPage(page);
            return document;
        }

        @Override
        protected void onPostExecute(PdfDocument document) {
            String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
            File file = new File(pdfPath, "Report.pdf");
            try {
                FileOutputStream fos = new FileOutputStream(file);
                document.writeTo(fos);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            document.close();
        }

        private Bitmap getBitmapFromURL(String src) {
            try {
                URL url = new URL(src);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                return BitmapFactory.decodeStream(input);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}