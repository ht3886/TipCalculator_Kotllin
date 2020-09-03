package com.example.mytipcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.SeekBar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"
private const val DEFAULT_TIP = 15
private const val DEFAULT_PERSON = 1
private const val DEFAULT_BILL = 0

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initializing default values
        tvTipPercent.text = "$DEFAULT_TIP%"
        etBill.setText("$DEFAULT_BILL")
        tvPeopleCount.text = "$DEFAULT_PERSON"
        seekBar.progress = DEFAULT_TIP

        //responding to user input to seekbar
        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                //logging progress value for debugging purpose
                Log.i(TAG, "onProgressChanged $progress")

                //synchronizing seekbar and textview
                tvTipPercent.text = "$progress%"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        //responding to user input for bill value
        etBill.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(bill_value: Editable?) {
                //logging bill value for debugging purpose
                Log.i(TAG, "afterTextChanged $bill_value")
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        //Addition button
        ivAddition.setOnClickListener{
            //handling validations
            if(tvPeopleCount.text.toString().isNotEmpty()){
                tvPeopleCount.text = (tvPeopleCount.text.toString().toInt() + 1).toString()
            }
        }

        //Subtraction button
        ivSubtraction.setOnClickListener{
            //handling validations
            if(tvPeopleCount.text.toString().toInt() > 1){
                tvPeopleCount.text = (tvPeopleCount.text.toString().toInt() - 1).toString()
            } else {
                Toast.makeText(this, "There should atleast be 1 person to pay", Toast.LENGTH_SHORT).show()
            }
        }

        btnCalculate.setOnClickListener{
            calculate()
        }

        btnClear.setOnClickListener{
            etBill.text.clear()
            tvPeopleCount.text ="1"
            seekBar.progress = DEFAULT_TIP
            calculate()
        }
    }

    //calculate logic starts
    private fun calculate() {
        if (etBill.text.isEmpty() || etBill.text.toString() == "."){
            tvTipValue.text = "$0.00"
            tvTotalValue.text = "$0.00"
            tvTipPersonValue.text = "$0.00"
            tvBillPersonValue.text = "$0.00"
            return
        }

        val billAmount  = etBill.text.toString().toDouble()
        val tipPercent = seekBar.progress
        val tipAmount = (billAmount * tipPercent)/100
        val totalBill = billAmount + tipAmount
        val tipPerson = tipAmount/tvPeopleCount.text.toString().toInt()
        val billPerson = totalBill/tvPeopleCount.text.toString().toInt()
        tvTipValue.text = "$"+"%.2f".format(tipAmount)
        tvTotalValue.text = "$"+"%.2f".format(totalBill)
        tvTipPersonValue.text = "$"+"%.2f".format(tipPerson)
        tvBillPersonValue.text = "$"+"%.2f".format(billPerson)
    }
}