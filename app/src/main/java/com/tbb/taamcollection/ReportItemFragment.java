package com.tbb.taamcollection;

import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;
import java.io.File;


import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.collection.LLRBNode;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;

public class ReportItemFragment extends Fragment {
    ItemDatabase db;
    // Default constructor
    public ReportItemFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // The layout file should be created in the res/layout directory
        View view = inflater.inflate(R.layout.report_item_fragment, container, false);
        db = new ItemDatabase("items");
        Button lotButton = view.findViewById(R.id.lot_submit);
        Button nameButton = view.findViewById(R.id.name_submit);
        Button catButton = view.findViewById(R.id.cat_submit);
        Button catdpButton = view.findViewById(R.id.cat_dpsubmit);
        Button periodButton = view.findViewById(R.id.period_submit);
        Button periodDPButton = view.findViewById(R.id.period_dpsubmit);

        TextInputEditText lotText = view.findViewById(R.id.lot_input);
        TextInputEditText nameText = view.findViewById(R.id.name_input);
        TextInputEditText catText = view.findViewById(R.id.cat_input);
        TextInputEditText catdpText = view.findViewById(R.id.cat_dpinput);
        TextInputEditText periodText = view.findViewById(R.id.period_input);
        TextInputEditText periodDPText = view.findViewById(R.id.period_dpinput);

        Button allButton = view.findViewById(R.id.all_submit);
        Button allDPButton = view.findViewById(R.id.all_dpsubmit);





        lotButton.setOnClickListener(new View.OnClickListener(){ //LOT SEARCH
            @Override
            public void onClick(View v){
                LinkedList<Item> itemsList = db.search("",lotText.getText().toString(),"","");
                for(Item item : itemsList){
                    System.out.println("TEST");
                }
                System.out.println(lotText.toString());
                createPDF(itemsList);


            }
        });
        nameButton.setOnClickListener(new View.OnClickListener(){ //NAME SEARCH
            @Override
            public void onClick(View v){
                LinkedList<Item> itemsList = db.search(nameText.toString(),"","","");



            }
        });
        catButton.setOnClickListener(new View.OnClickListener(){ // CATEGORY SEARCH
            @Override
            public void onClick(View v){
                LinkedList<Item> itemsList = db.search("","",catText.toString(),"");



            }
        });

        catdpButton.setOnClickListener(new View.OnClickListener(){ // CATEGORY W/ DESC. & PIC
            @Override
            public void onClick(View v){
                LinkedList<Item> itemsList = db.search("","",catdpText.toString(),"");



            }
        });

        periodButton.setOnClickListener(new View.OnClickListener(){ // PERIOD SEARCH
            @Override
            public void onClick(View v){
                LinkedList<Item> itemsList = db.search("","","",periodText.toString());



            }
        });
        periodDPButton.setOnClickListener(new View.OnClickListener(){ // PERIOD W/ DESC. & PIC SEARCH
            @Override
            public void onClick(View v){
                LinkedList<Item> itemsList = db.search("","","",periodDPText.toString());



            }
        });




        return view;
    }


    // let it accept all diff parameters, make if statements that handle if the other arguments are empty
    private void createPDF(LinkedList<Item> itemsList){

        int page_num = 1;
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595,842,page_num).create();

        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        canvas.drawColor(Color.WHITE);

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);

        paint.setTextSize(42);
        float x = 50;
        float y = 200;
        canvas.drawText("Items: ", 50, 150, paint);
        canvas.drawText("Names: ", 400, 150, paint);
        for(Item item : itemsList) {

            System.out.println(item.getName());
            canvas.drawText(item.getName(), x, y, paint);
            if(y+50 >= 800){ // Creates new page
                document.finishPage(page);
                pageInfo = new PdfDocument.PageInfo.Builder(595,842,page_num++).create();
                page = document.startPage(pageInfo);
                canvas = page.getCanvas();
            }
            y+=50;

        }
        System.out.println("hello");

        document.finishPage(page);

        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(pdfPath, "Report.pdf");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            document.writeTo(fos);
        } catch (FileNotFoundException e){
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}